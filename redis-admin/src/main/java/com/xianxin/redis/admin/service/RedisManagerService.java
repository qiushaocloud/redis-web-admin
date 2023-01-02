package com.xianxin.redis.admin.service;

import com.xianxin.redis.admin.bean.dto.RedisConfigDTO;
import com.xianxin.redis.admin.bean.dto.SelectDBIndexDTO;
import com.xianxin.redis.admin.bean.po.RedisConfig;
import com.xianxin.redis.admin.bean.po.SysUser;
import com.xianxin.redis.admin.framework.common.Response;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Redis 配置管理service
 *
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
public interface RedisManagerService {

    Response<List<RedisConfig>> list();

    Response<Boolean> saveOrUpdateConfig(RedisConfig config);

    Response<Boolean> enabledOrDisabled(String host, int port, boolean status);

    Response<Boolean> deleteConfig(String host, int port);


    /**
     * 根据host 获取ERR数据库索引
     *
     * @param host IP or URL地址
     * @return ERR数据库索引 集合
     */
    Response<List<SelectDBIndexDTO>> selectDBIndex(String host, int port);

    RedisConfig getRedisConfig(String host, int port);

    Jedis getJedis(RedisConfig redisConfig, String db);

    Response<List<RedisConfigDTO>> select(SysUser sysUser);
}
