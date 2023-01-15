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
public class RedisOperationsForZSetImpl extends RedisOperations {
    @Override
    public SysRedisDTO getInfo(Jedis jedis, String key) {
        SysRedisDTO dto = new SysRedisDTO();

        Long elCount = jedis.zcard(key);
        long zcard = jedis.zcard(key);
        Set<String> zset = jedis.zrange(key, 0, zcard);
        if (zset != null && zset.size() > 0) {
            List<Map<String, Object>> values = new ArrayList<>();
            int no = 1;
            for (String m : zset) {
                Map<String, Object> map = new HashMap<>();
                map.put("no", no);
                map.put("zvalue", m);
                double zscore = jedis.zscore(key, m);
                map.put("zscore", zscore);
                values.add(map);
                no++;
            }
            dto.setValues(values);
        }

        dto.setDataType(ConSts.ZSET);
        dto.setElCount(elCount);
        dto.setRedisKey(key);
        dto.setOldRedisKey(key);
        return dto;
    }

    @Override
    public boolean set(Jedis jedis, CacheRedisVO vo) {
        if (StringUtils.isNotBlank(vo.getOldRedisKey())) {
            // 修改 删除以前的key
            jedis.zrem(vo.getRedisKey(), vo.getOldRedisKey());
            log.info("zset - {} 从 {} 移除", vo.getOldRedisKey(), vo.getRedisKey());
        }
        // 新增
        jedis.zadd(vo.getRedisKey(), vo.getScore(), vo.getRedisValue());
        return true;
    }

    @Override
    protected long del(Jedis jedis, CacheRedisVO vo) {
        long count = 0;
        if (StringUtils.isNotBlank(vo.getRedisValue())) {
            jedis.zrem(vo.getRedisKey(), vo.getRedisValue());
            log.info("zset - 单个 {} 从 {} 中移除", vo.getRedisValue(), vo.getRedisKey());
            count = 1;
        } else {
            long zcard = jedis.zcard(vo.getRedisKey());
            Set<String> zset = jedis.zrange(vo.getRedisKey(), 0, zcard);
            //log.info("zset size={}", zset.size());
            for (String s : zset) {
                jedis.zrem(vo.getRedisKey(), s);
                count++;
            }
            log.info("zset - 移除全部");
        }
        return count;
    }
}
