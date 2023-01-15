package com.xianxin.redis.admin.bean.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRedis implements Serializable {
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 原始的缓存key
     */
    private String oldRedisKey;
    /**
     * 缓存键
     */
    private String redisKey;
    /**
     * 缓存值
     */
    private String redisValue;
    /**
     * 过期时间
     */
    private String expire;
    /**
     * 集合元素总数
     */
    private Long elCount;

    public SysRedis() {

    }

    public SysRedis(String dataType, String redisKey, String redisValue, String expire) {
        this.dataType = dataType;
        this.redisKey = redisKey;
        this.redisValue = redisValue;
        this.expire = expire;
    }
}
