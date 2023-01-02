package com.xuebusi.cms.base.modules.redis.entity;

import com.xuebusi.cms.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 集群配置Entity
 *
 * @author shiyanjun
 * @version 2018-11-14
 */
public class RedisCluster extends DataEntity<RedisCluster> {

    private static final long serialVersionUID = 1L;
    private String redisHostName;        // redis主机名
    private Integer redisPort;        // redis端口

    public RedisCluster() {
        super();
    }

    public RedisCluster(String id) {
        super(id);
    }

    @Length(min = 1, max = 100, message = "redis主机名长度必须介于 1 和 100 之间")
    public String getRedisHostName() {
        return redisHostName;
    }

    public void setRedisHostName(String redisHostName) {
        this.redisHostName = redisHostName;
    }

    @NotNull(message = "redis端口不能为空")
    public Integer getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(Integer redisPort) {
        this.redisPort = redisPort;
    }

}