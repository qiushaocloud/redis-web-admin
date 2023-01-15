package com.xianxin.redis.admin.service.impl;

import com.xianxin.redis.admin.bean.dto.SysRedisDTO;
import com.xianxin.redis.admin.bean.vo.CacheRedisVO;
import com.xianxin.redis.admin.framework.common.ConSts;
import com.xianxin.redis.admin.service.RedisOperations;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RedisOperationsForHashImpl extends RedisOperations {

    @Override
    public SysRedisDTO getInfo(Jedis jedis, String key) {
        SysRedisDTO dto = new SysRedisDTO();

        Long elCount = jedis.hlen(key);

        Map<String, String> hgetAll = jedis.hgetAll(key);
        if (hgetAll != null && hgetAll.size() > 0) {
            List<Map<String, Object>> values = new ArrayList<>();
            int no = 1;
            for (String hk : hgetAll.keySet()) {
                Map<String, Object> map = new HashMap<>();
                map.put("no", no);
                map.put("hkey", hk);
                map.put("hvalue", hgetAll.get(hk));
                values.add(map);
                no++;
            }
            dto.setValues(values);
        }

        dto.setDataType(ConSts.HASH);
        dto.setElCount(elCount);
        dto.setRedisKey(key);
        dto.setOldRedisKey(key);
        return dto;
    }

    @Override
    public boolean set(Jedis jedis, CacheRedisVO vo) {
        if (StringUtils.isNotBlank(vo.getOldRedisKey())) {
            // 修改 删除以前的key
            jedis.hdel(vo.getRedisKey(), vo.getOldRedisKey());
            log.info("hash - {} 从 {} 移除", vo.getOldRedisKey(), vo.getRedisKey());
        }
        //新增
        jedis.hset(vo.getRedisKey(), vo.getRedisHKey(), vo.getRedisValue());

        return true;
    }

    @Override
    protected long del(Jedis jedis, CacheRedisVO vo) {
        long count = 0;

        if (StringUtils.isNotBlank(vo.getRedisKey()) && StringUtils.isNotBlank(vo.getRedisHKey())) {
            count = jedis.hdel(vo.getRedisKey(), vo.getRedisHKey());
            log.info("hash - 单个 {} 从 {} 中移除", vo.getRedisHKey(), vo.getRedisKey());
        } else {
            Map<String, String> hgetAll = jedis.hgetAll(vo.getRedisKey());
            if (hgetAll != null && hgetAll.size() > 0) {
                for (String hk : hgetAll.keySet()) {
                    count += jedis.hdel(vo.getRedisKey(), hk);
                }
                log.info("hash - 移除全部");
            }
        }

        return count;
    }
}
