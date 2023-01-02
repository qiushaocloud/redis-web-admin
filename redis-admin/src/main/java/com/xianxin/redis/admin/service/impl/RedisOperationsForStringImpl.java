package com.xianxin.redis.admin.service.impl;

import com.xianxin.redis.admin.bean.dto.SysRedisDTO;
import com.xianxin.redis.admin.bean.vo.CacheRedisVO;
import com.xianxin.redis.admin.framework.common.ConSts;
import com.xianxin.redis.admin.framework.utils.DateUtils;
import com.xianxin.redis.admin.service.RedisOperations;
import redis.clients.jedis.Jedis;

public class RedisOperationsForStringImpl extends RedisOperations {

    @Override
    public SysRedisDTO getInfo(Jedis jedis, String key) {
        SysRedisDTO dto = new SysRedisDTO();

        String value = jedis.get(key);
        dto.setRedisValue(value);

        dto.setDataType(ConSts.STRIGN);

        Long expire = jedis.ttl(key);

        String expireStr = "";
        if (expire == -1) {
            expireStr = "永不过期";
        } else {
            expireStr = DateUtils.getTimeStrBySecond(expire);
        }
        // 过期时间 数值类型
        dto.setExpire(expire.toString());
        dto.setExpireStr(expireStr);

        dto.setElCount(1L);
        dto.setRedisKey(key);
        dto.setOldRedisKey(key);

        return dto;
    }

    @Override
    public boolean set(Jedis jedis, CacheRedisVO vo) {

        jedis.set(vo.getRedisKey(), vo.getRedisValue());

        return true;
    }

    @Override
    protected long del(Jedis jedis, CacheRedisVO vo) {

        return jedis.del(vo.getRedisKey());
    }
}
