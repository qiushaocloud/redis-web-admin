package com.xianxin.redis.admin.service.impl;

import com.xianxin.redis.admin.bean.dto.SysRedisDTO;
import com.xianxin.redis.admin.bean.po.RedisConfig;
import com.xianxin.redis.admin.bean.po.SysRedis;
import com.xianxin.redis.admin.bean.vo.CachPublishVO;
import com.xianxin.redis.admin.bean.vo.CacheRedisQueryVO;
import com.xianxin.redis.admin.bean.vo.CacheRedisVO;
import com.xianxin.redis.admin.bean.vo.CacheSynchVO;
import com.xianxin.redis.admin.framework.common.ConSts;
import com.xianxin.redis.admin.framework.common.Response;
import com.xianxin.redis.admin.framework.factory.RedisOperationsFactory;
import com.xianxin.redis.admin.framework.utils.DateUtils;
import com.xianxin.redis.admin.service.RedisManagerService;
import com.xianxin.redis.admin.service.RedisOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class RedisOperationServiceImpl implements RedisOperationService {

    @Autowired
    private RedisManagerService redisManagerService;

    @Override
    public Response<Long> cachePublish(CachPublishVO vo) {
        Jedis jedis = null;
        try {

            jedis = getJedis(getRedisConfig(vo.getHost(), vo.getPort()), vo.getDb());

            Long publish = jedis.publish(vo.getChannel(), vo.getMessage());

            log.info("{} 推送消息：{}", vo.getChannel(), vo.getMessage());

            return Response.success(200, "推送成功", publish);
        } finally {
            assert jedis != null;
            jedis.close();
        }

    }

    @Override
    public Response<List<SysRedis>> cacheList(CacheRedisQueryVO vo) {

        RedisConfig redisConfig = getRedisConfig(vo.getHost(), vo.getPort());

        Jedis jedis = getJedis(redisConfig, vo.getDb());

        String match = "*";
        if (StringUtils.isNotBlank(vo.getKeyword())) {
            match = "*" + vo.getKeyword() + "*";
        } else {
            if ("prod".equals(redisConfig.getEnv())) {
                return Response.error("线上环境不支持查询全部");
            }
        }

        ScanParams scanParams = new ScanParams();
        int count = 10000;
        scanParams.count(count);

        scanParams.match(match);
        String scanRet = "0";

        List<String> keyList = new ArrayList<>();

        do {
            ScanResult<String> scanResult = jedis.scan(scanRet, scanParams);
            scanRet = scanResult.getCursor();
            //scan 487439 MATCH * COUNT 10000
            log.info("scan {} MATCH {} COUNT {}", scanRet, vo.getKeyword(), count);
            keyList.addAll(scanResult.getResult());
        } while (!"0".equals(scanRet));

        if (!CollectionUtils.isEmpty(keyList)) {
            List<SysRedis> list = new ArrayList<>();
            int start = vo.getPageNo() == 1 ? 0 : (vo.getPageNo() - 1) * vo.getPageSize();
            for (int i = start, j = 0; i < keyList.size() && j < vo.getPageSize(); i++, j++) {
                String key = keyList.get(i);
                SysRedis base = baseInfo(key, jedis);
                list.add(base);
            }
            jedis.close();
            int total = keyList.size();
            // 清空加载出来全部
            keyList.clear();
            return Response.success(200, total, list);
        }
        return Response.success(200, 0, "暂无数据");
    }

    @Override
    public Response<SysRedisDTO> cacheDetails(CacheRedisQueryVO vo) {

        SysRedisDTO dto = RedisOperationsFactory.build(vo.getType()).details(getJedis(getRedisConfig(vo.getHost(), vo.getPort()), vo.getDb()), vo);

        return Response.success(dto.getElCount(), dto);
    }

    @Override
    public Response<Boolean> cacheCreate(CacheRedisVO vo) {

        boolean b = RedisOperationsFactory.build(vo.getDataType()).create(getJedis(getRedisConfig(vo.getHost(), vo.getPort()), vo.getDb()), vo);

        return Response.success(200, "保存成功", b);
    }

    @Override
    public Response<Boolean> cacheDelete(CacheRedisVO vo) {

        long count = RedisOperationsFactory.build(vo.getDataType()).delete(getJedis(getRedisConfig(vo.getHost(), vo.getPort()), vo.getDb()), vo);

        return Response.success(200, count, "删除" + count + "条缓存");
    }

    @Override
    public Response<Boolean> cacheNameUpdate(CacheRedisVO vo) {

        RedisOperationsFactory.build(vo.getDataType()).rename(getJedis(getRedisConfig(vo.getHost(), vo.getPort()), vo.getDb()), vo);

        return Response.success(200, "修改成功", Boolean.TRUE);
    }

    @Override
    public Response<Boolean> cacheValueCreate(CacheRedisVO vo) {

        return cacheCreate(vo);
    }

    @Override
    public Response<Boolean> cacheValueUpdate(CacheRedisVO vo) {

        return cacheCreate(vo);
    }

    @Override
    public Response<Boolean> cacheExpireUpdate(CacheRedisVO vo) {
        Jedis jedis = getJedis(getRedisConfig(vo.getHost(), vo.getPort()), vo.getDb());

        try {
            RedisOperationsFactory.build(ConSts.STRIGN).setRedisExpire(jedis, vo.getRedisKey(), vo.getExpire(), vo.getUnit());
            return Response.success("过期时间更新成功");
        } catch (Exception e) {
            log.error("更新缓存失效时间出现错误", e);
            return Response.error("过期时间更新失败");
        } finally {
            jedis.close();
        }
    }

    @Override
    public Response<Boolean> cacheSynch(CacheSynchVO vo) {
        // 查询【源】缓存的详情
        CacheRedisQueryVO cacheRedisQueryVo = new CacheRedisQueryVO();
        BeanUtils.copyProperties(vo, cacheRedisQueryVo);
        Response<SysRedisDTO> redisDtoResponse = cacheDetails(cacheRedisQueryVo);

        if (redisDtoResponse.getCode() == HttpStatus.OK.value()) {
            SysRedisDTO sysRedisDto = redisDtoResponse.getData();
            String type = sysRedisDto.getDataType();

            // 创建【目标】缓存
            CacheRedisVO cacheRedisVo = new CacheRedisVO();
            cacheRedisVo.setHost(vo.getTargetHost());
            cacheRedisVo.setPort(vo.getTargetPort());
            cacheRedisVo.setDb(vo.getTargetDb());
            cacheRedisVo.setDataType(type);

            int expire = StringUtils.isNotBlank(sysRedisDto.getExpire()) ? Integer.parseInt(sysRedisDto.getExpire()) : -1;
            cacheRedisVo.setExpire(expire);

            cacheRedisVo.setRedisKey(sysRedisDto.getRedisKey());

            if (ConSts.STRIGN.equals(type)) {
                cacheRedisVo.setRedisValue(sysRedisDto.getRedisValue());

                Response<Boolean> cacheCreateString = cacheCreate(cacheRedisVo);
                String msg = cacheCreateString.getCode() == HttpStatus.OK.value() ? "同步成功" : "同步失败";
                log.info("string - {} {}", sysRedisDto.getRedisKey(), msg);
                cacheCreateString.setMsg(msg);

                return cacheCreateString;
            }

            return assemblyCacheSynch(redisDtoResponse, cacheRedisVo, vo, cacheRedisQueryVo);
        }

        return Response.error(redisDtoResponse.getCode(), redisDtoResponse.getMsg());
    }

    private Response<Boolean> assemblyCacheSynch(Response<SysRedisDTO> redisDtoResponse, CacheRedisVO cacheRedisVo, CacheSynchVO vo, CacheRedisQueryVO cacheRedisQueryVo) {

        SysRedisDTO sysRedisDto = redisDtoResponse.getData();

        List<Map<String, Object>> values = sysRedisDto.getValues();

        String type = sysRedisDto.getDataType();

        if (values != null && values.size() > 0) {
            // 循环存储缓存
            AtomicReference<Response<Boolean>> responseAtomicReference = new AtomicReference<>();
            values.forEach(val -> {

                if (ConSts.LIST.equals(type) || ConSts.SET.equals(type)) {

                    Object svalue = val.get("svalue");
                    cacheRedisVo.setRedisValue(svalue.toString());

                } else if (ConSts.HASH.equals(type)) {

                    Object hkey = val.get("hkey");
                    Object hvalue = val.get("hvalue");
                    cacheRedisVo.setRedisHKey(hkey.toString());
                    cacheRedisVo.setRedisValue(hvalue.toString());

                } else if (ConSts.ZSET.equals(type)) {
                    Object zvalue = val.get("zvalue");
                    Object zscore = val.get("zscore");

                    cacheRedisVo.setScore(Double.parseDouble(zscore.toString()));
                    cacheRedisVo.setRedisValue(zvalue.toString());
                }

                Response<Boolean> response = cacheCreate(cacheRedisVo);
                responseAtomicReference.set(response);
                String msg = responseAtomicReference.get().getCode() == HttpStatus.OK.value() ? "同步成功" : "同步失败";
                log.info("{} - {} {}", type, sysRedisDto.getRedisKey(), msg);
            });

            if (responseAtomicReference.get().getCode() == HttpStatus.OK.value()) {
                long pageCount = (redisDtoResponse.getCount() + cacheRedisQueryVo.getPageSize() - 1) / cacheRedisQueryVo.getPageSize();
                int nextPage = cacheRedisQueryVo.getPageNo() + 1;

                log.info("{} - 总条数：{}，总页数：{}，当前页：{}，下一页：{}", type, redisDtoResponse.getCount(), pageCount, cacheRedisQueryVo.getPageNo(), nextPage);
                boolean isNextPage = nextPage <= pageCount;
                log.info("是否自动查询下一页数据：{}", isNextPage);
                if (isNextPage) {
                    vo.setPageNo(nextPage);
                    cacheSynch(vo);
                }
            }

            Response<Boolean> response = responseAtomicReference.get();
            String msg = responseAtomicReference.get().getCode() == HttpStatus.OK.value() ? "同步成功" : "同步失败";
            log.info("{} - {}", type, msg);
            response.setMsg(msg);
            return response;
        }
        return Response.error("同步失败");
    }

    private SysRedis baseInfo(String key, Jedis jedis) {
        String type = jedis.type(key);
        Long expire = jedis.ttl(key);
        log.info("key={}，type={}", key, type);
        String expireStr = "";
        if (expire == -1) {
            expireStr = "永不过期";
        } else {
            expireStr = DateUtils.getTimeStrBySecond(expire);
        }

        SysRedis sysRedis = new SysRedis(type, key, null, expireStr);

        Long elCount = 0L;

        if (ConSts.STRIGN.equals(type)) {
            elCount = 1L;
        } else if (ConSts.LIST.equals(type)) {
            elCount = jedis.llen(key);
        } else if (ConSts.HASH.equals(type)) {
            elCount = jedis.hlen(key);
        } else if (ConSts.SET.equals(type)) {
            elCount = jedis.scard(key);
        } else if (ConSts.ZSET.equals(type)) {
            elCount = jedis.zcard(key);
        }

        sysRedis.setElCount(elCount);
        return sysRedis;
    }

    private Jedis getJedis(RedisConfig redisConfig, String db) {
        return redisManagerService.getJedis(redisConfig, db);
    }

    private RedisConfig getRedisConfig(String host, int port) {
        return redisManagerService.getRedisConfig(host, port);
    }

}
