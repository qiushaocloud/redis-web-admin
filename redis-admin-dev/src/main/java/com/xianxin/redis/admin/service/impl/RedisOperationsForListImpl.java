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
public class RedisOperationsForListImpl extends RedisOperations {

    @Override
    public SysRedisDTO getInfo(Jedis jedis, String key) {
        SysRedisDTO dto = new SysRedisDTO();
        Long elCount = jedis.llen(key);

        if (elCount > 0) {
            List<String> listValues = jedis.lrange(key, 0, elCount);
            if (listValues != null && listValues.size() > 0) {
                List<Map<String, Object>> values = new ArrayList<>();
                int no = 1;
                for (String m : listValues) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("no", no);
                    map.put("svalue", m);
                    values.add(map);
                    no++;
                }
                dto.setValues(values);
            }
        }

        dto.setDataType(ConSts.LIST);
        dto.setElCount(elCount);
        dto.setRedisKey(key);
        dto.setOldRedisKey(key);

        return dto;
    }

    @Override
    public boolean set(Jedis jedis, CacheRedisVO vo) {

        // 原始value
        if (StringUtils.isNotBlank(vo.getOldRedisKey())) {
            jedis.lrem(vo.getRedisKey(), 1, vo.getOldRedisKey());
            log.info("list - {} 从 {} 移除", vo.getOldRedisKey(), vo.getRedisKey());
        }

        // 将一个值插入到已存在的列表头部
        jedis.lpush(vo.getRedisKey(), vo.getRedisValue());

        return false;
    }

    @Override
    protected long del(Jedis jedis, CacheRedisVO vo) {
        long count = 0;
        // 删除单个
        if (StringUtils.isNotBlank(vo.getRedisValue())) {
            count = 1;
            jedis.lrem(vo.getRedisKey(), 1, vo.getRedisValue());
            log.info("list - 单个 {} 从 {} 中移除", vo.getRedisValue(), vo.getRedisKey());
        } else {
            long len = jedis.llen(vo.getRedisKey());
            if (len > 0) {
                List<String> lvalues = jedis.lrange(vo.getRedisKey(), 0, len);
                for (String value : lvalues) {
                    jedis.lrem(vo.getRedisKey(), 1, value);
                    count++;
                }
                log.info("list - 移除全部");
            }
        }
        // 删除全部
        return count;
    }
}
