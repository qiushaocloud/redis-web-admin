package com.xianxin.redis.admin.service.impl;

import com.xianxin.redis.admin.bean.dto.SysRedisDTO;
import com.xianxin.redis.admin.bean.vo.CacheRedisVO;
import com.xianxin.redis.admin.framework.common.ConSts;
import com.xianxin.redis.admin.service.RedisOperations;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.*;

@Slf4j
public class RedisOperationsForSetImpl extends RedisOperations {

    @Override
    public SysRedisDTO getInfo(Jedis jedis, String key) {
        SysRedisDTO dto = new SysRedisDTO();

        Long elCount = jedis.scard(key);
        Set<String> smembers = jedis.smembers(key);
        if (smembers != null && smembers.size() > 0) {
            List<Map<String, Object>> values = new ArrayList<>();
            int no = 1;
            for (String m : smembers) {
                Map<String, Object> map = new HashMap<>();
                map.put("no", no);
                map.put("svalue", m);
                values.add(map);
                no++;
            }
            dto.setValues(values);
        }

        dto.setDataType(ConSts.SET);
        dto.setElCount(elCount);
        dto.setRedisKey(key);
        dto.setOldRedisKey(key);

        return dto;
    }

    @Override
    public boolean set(Jedis jedis, CacheRedisVO vo) {
        if (StringUtils.isNotBlank(vo.getOldRedisKey())) {
            // 修改 删除以前的key
            jedis.srem(vo.getRedisKey(), vo.getOldRedisKey());
            log.info("set - {} 从 {} 移除", vo.getOldRedisKey(), vo.getRedisKey());
        }
        // 新增
        jedis.sadd(vo.getRedisKey(), vo.getRedisValue());
        return true;
    }

    @Override
    protected long del(Jedis jedis, CacheRedisVO vo) {

        if (StringUtils.isNotBlank(vo.getRedisValue())) {
            jedis.srem(vo.getRedisKey(), vo.getRedisValue());
            log.info("set - 单个 {} 从 {} 中移除", vo.getRedisValue(), vo.getRedisKey());
        } else {
            long elCount = jedis.scard(vo.getRedisKey());
            for (long i = 0; i < elCount; i++) {
                // 删除全部
                jedis.spop(vo.getRedisKey());
                log.info("set - 移除 {} - 计数：{}", vo.getRedisKey(), (i + 1));
            }
            log.info("set - 移除全部完成");
        }
        return 1;
    }
}
