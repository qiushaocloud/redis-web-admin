package com.xianxin.redis.admin.service;

import com.xianxin.redis.admin.bean.dto.SysRedisDTO;
import com.xianxin.redis.admin.bean.po.RedisExpire;
import com.xianxin.redis.admin.bean.vo.CacheRedisQueryVO;
import com.xianxin.redis.admin.bean.vo.CacheRedisVO;
import com.xianxin.redis.admin.framework.common.ConSts;
import com.xianxin.redis.admin.framework.exception.ServerException;
import com.xianxin.redis.admin.framework.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class RedisOperations {

    public abstract SysRedisDTO getInfo(Jedis jedis, String keyword);

    public SysRedisDTO valuesSplit(SysRedisDTO dto, String cacheKeyword, int pageNo, int pageSize) {

        if (dto.getValues() != null && dto.getValues().size() > 0) {
            // 过滤查询值列表关键字
            List<Map<String, Object>> valueAll = dto.getValues();
            List<Map<String, Object>> filterValueAll = new ArrayList<>();

            if (StringUtils.isNotBlank(cacheKeyword)) {

                for (int i = 0; i < valueAll.size(); i++) {

                    Map<String, Object> map = valueAll.get(i);

                    String hk = "";
                    if (ConSts.HASH.equals(dto.getDataType())) {
                        hk = map.get("hkey").toString();
                    } else if (ConSts.ZSET.equals(dto.getDataType())) {
                        hk = map.get("zvalue").toString();
                    } else if (ConSts.SET.equals(dto.getDataType())) {
                        hk = map.get("svalue").toString();
                    } else if (ConSts.LIST.equals(dto.getDataType())) {
                        hk = map.get("svalue").toString();
                    }

                    if (hk.contains(cacheKeyword)) {
                        filterValueAll.add(map);
                    }
                }

                long elCount = Long.parseLong(filterValueAll.size() + "");

                dto.setElCount(elCount);
            } else {
                filterValueAll.addAll(valueAll);
            }

            // 分页
            List<Map<String, Object>> values = new ArrayList<>();

            int start = pageNo == 1 ? 0 : (pageNo - 1) * pageSize;

            for (int i = start, j = 0; i < filterValueAll.size() && j < pageSize; i++, j++) {
                Map<String, Object> map = filterValueAll.get(i);
                values.add(map);
            }

            dto.setValues(values);
        }
        return dto;
    }

    public SysRedisDTO details(Jedis jedis, CacheRedisQueryVO vo) {

        SysRedisDTO dto = getInfo(jedis, vo.getKeyword());

        //查询过期时间
        RedisExpire redisExpire = getRedisExpire(jedis, vo.getKeyword());
        dto.setExpire(String.valueOf(redisExpire.getExpire()));
        dto.setExpireStr(redisExpire.getExpireStr());

        valuesSplit(dto, vo.getCacheKeyword(), vo.getPageNo(), vo.getPageSize());

        jedis.close();

        return dto;
    }

    public RedisExpire getRedisExpire(Jedis jedis, String keyword) {
        Long expire = jedis.ttl(keyword);
        String expireStr = "";
        if (expire == -1) {
            expireStr = "永不过期";
        } else {
            expireStr = DateUtils.getTimeStrBySecond(expire);
        }
        return new RedisExpire(expire, expireStr);
    }

    public abstract boolean set(Jedis jedis, CacheRedisVO vo);

    public boolean create(Jedis jedis, CacheRedisVO vo) {
        try {
            boolean exists = jedis.exists(vo.getRedisKey());
            if (exists) {
                Long expire = jedis.ttl(vo.getRedisKey());
                vo.setExpire(Integer.parseInt(expire + ""));
            }

            set(jedis, vo);

            setRedisExpire(jedis, vo.getRedisKey(), vo.getExpire(), vo.getUnit());

            return true;
        } catch (Exception e) {
            if (e instanceof JedisDataException) {
                //哨兵模式时 从节点写操作就会报这个异常
                if ("READONLY You can't write against a read only slave.".equals(e.getMessage())) {
                    throw new ServerException("从节点只能读不能写");
                }
            }
            throw new ServerException("添加缓存出现异常");
        } finally {
            jedis.close();
        }
    }

    public void setRedisExpire(Jedis jedis, String redisKey, int expire, String unit) {
        boolean exists = jedis.exists(redisKey);
        if (!exists) {
            throw new ServerException("缓存Key不存在");
        }

        if (expire > 0) {
            if (StringUtils.isNotBlank(unit)) {
                //根据单位计算
                jedis.expire(redisKey, DateUtils.getExpire(expire, unit));
            } else {
                //默认是秒
                jedis.expire(redisKey, expire);
            }
        } else {
            // 如果小于0 就移除key的有效时间
            jedis.persist(redisKey);
        }
    }

    /**
     * 删除缓存
     *
     * @param jedis
     * @param vo
     * @return 返回-1 表示不存在
     */
    public long delete(Jedis jedis, CacheRedisVO vo) {
        try {
            boolean exists = jedis.exists(vo.getRedisKey());
            if (!exists) {
                throw new ServerException("缓存Key不存在");
            }
            return del(jedis, vo);
        } catch (Exception e) {
            throw new ServerException("删除缓存出现异常");
        } finally {
            jedis.close();
        }
    }

    protected abstract long del(Jedis jedis, CacheRedisVO vo);

    public String rename(Jedis jedis, CacheRedisVO vo) {
        boolean exists = jedis.exists(vo.getOldRedisKey());
        if (!exists) {
            throw new ServerException("缓存Key不存在");
        }
        return jedis.rename(vo.getOldRedisKey(), vo.getRedisKey());
    }
}
