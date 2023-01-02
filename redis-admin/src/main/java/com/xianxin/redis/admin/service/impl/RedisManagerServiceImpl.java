package com.xianxin.redis.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.xianxin.redis.admin.bean.dto.RedisConfigDTO;
import com.xianxin.redis.admin.bean.dto.SelectDBIndexDTO;
import com.xianxin.redis.admin.bean.enu.RoleType;
import com.xianxin.redis.admin.bean.po.RedisConfig;
import com.xianxin.redis.admin.bean.po.SysUser;
import com.xianxin.redis.admin.bean.po.SysUserHost;
import com.xianxin.redis.admin.dao.RedisConfigRepository;
import com.xianxin.redis.admin.dao.SysUserHostRepository;
import com.xianxin.redis.admin.framework.common.Response;
import com.xianxin.redis.admin.framework.exception.ServerException;
import com.xianxin.redis.admin.service.RedisManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RedisManagerServiceImpl implements RedisManagerService {

    @Autowired
    private RedisConfigRepository redisConfigRepository;

    @Autowired
    private SysUserHostRepository sysUserHostRepository;

    @Override
    public Response<List<RedisConfigDTO>> select(SysUser sysUser) {
        List<RedisConfigDTO> list = new ArrayList<>();

        //管理员用户直接所有启用的server
        if (RoleType.ADMIN.name().equals(sysUser.getRole())) {
            List<RedisConfig> all = redisConfigRepository.findAll();
            for (RedisConfig redisConfig : all) {
                if (redisConfig != null && Boolean.TRUE.equals(redisConfig.getStatus())) {
                    RedisConfigDTO dto = new RedisConfigDTO();
                    BeanUtil.copyProperties(redisConfig, dto);
                    list.add(dto);
                }
            }
        } else {

            List<SysUserHost> sysUserHostList = sysUserHostRepository.findByUsername(sysUser.getUsername());
            for (SysUserHost sysUserHost : sysUserHostList) {
                RedisConfig redisConfig = redisConfigRepository.findByHostAndPort(sysUserHost.getHost(), sysUserHost.getPort());

                if (redisConfig != null && Boolean.TRUE.equals(redisConfig.getStatus())) {
                    RedisConfigDTO dto = new RedisConfigDTO();
                    BeanUtil.copyProperties(redisConfig, dto);
                    list.add(dto);
                }
            }
        }
        return Response.success(list);
    }

    @Override
    public Jedis getJedis(RedisConfig redisConfig, String db) {
        try {
            JedisShardInfo shardInfo = new JedisShardInfo("redis://" + redisConfig.getHost() + ":" + redisConfig.getPort() + "/" + db);
            if (StringUtils.isNotBlank(redisConfig.getPassword())) {
                shardInfo.setPassword(redisConfig.getPassword());
            } else {
                shardInfo.setPassword(null);
            }
            return new Jedis(shardInfo);

        } catch (Exception e) {

            throw new ServerException(e.getMessage());
        }
    }

    @Override
    public RedisConfig getRedisConfig(String host, int port) {
        RedisConfig redisConfig = redisConfigRepository.findByHostAndPort(host, port);
        if (redisConfig == null) {
            throw new ServerException("redis-server配置不存在");
        }
        return redisConfig;
    }

    @Override
    public Response<List<RedisConfig>> list() {
        List<RedisConfig> list = redisConfigRepository.findAll();
        return Response.success(list.size(), list);
    }

    @Override
    public Response<Boolean> saveOrUpdateConfig(RedisConfig config) {

        RedisConfig entity = redisConfigRepository.findByHostAndPort(config.getHost(), config.getPort());

        if (entity != null && entity.getHost().equals(config.getHost()) && (StringUtils.isBlank(config.getId()) || !entity.getId().equals(config.getId()))) {
            return Response.error("redis-server已存在");
        }

        if (StringUtils.isBlank(config.getId())) {
            long nextId = IdUtil.createSnowflake(0, 0).nextId();
            config.setId("RC" + nextId);
        }

        redisConfigRepository.save(config);

        return Response.success(200, "保存成功", Boolean.TRUE);
    }


    @Override
    public Response<Boolean> enabledOrDisabled(String host, int port, boolean status) {

        RedisConfig entity = redisConfigRepository.findByHostAndPort(host, port);

        if (entity == null) {
            return Response.error("redis-server不存在");
        }

        entity.setStatus(status);

        redisConfigRepository.save(entity);

        return Response.success(200, (status ? "启用成功" : "停用成功"), Boolean.TRUE);
    }

    @Override
    public Response<Boolean> deleteConfig(String host, int port) {

        RedisConfig entity = redisConfigRepository.findByHostAndPort(host, port);

        if (entity == null) {
            return Response.error("redis-server不存在");
        }

        redisConfigRepository.delete(entity);

        return Response.success(200, "删除成功", Boolean.TRUE);
    }

    @Override
    public Response<List<SelectDBIndexDTO>> selectDBIndex(String host, int port) {
        log.info("HOST： {}", host);

        RedisConfig redisConfig = redisConfigRepository.findByHostAndPort(host, port);

        // 创建redis连接
        JedisShardInfo jedisShardInfo = new JedisShardInfo(host, redisConfig.getPort());
        if (StringUtils.isNotBlank(redisConfig.getPassword())) {
            jedisShardInfo.setPassword(redisConfig.getPassword());
        } else {
            jedisShardInfo.setPassword(null);
        }

        Jedis jedis = new Jedis(jedisShardInfo);

        List<SelectDBIndexDTO> list = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            log.info("SELECT {}", i);

            try {
                // 检查db是否连接扫描成功
                String selectDb = jedis.select(i);
                log.info(selectDb);

                SelectDBIndexDTO dbIndex = new SelectDBIndexDTO();
                dbIndex.setName("db" + i);
                dbIndex.setDb(i + "");
                list.add(dbIndex);
            } catch (Exception e) {
                log.info(e.getMessage());
                if (e instanceof JedisConnectionException) {
                    if (e.getMessage().contains("Unknown reply: N")) {
                        return Response.error("连接redis-server异常：" + e.getMessage());
                    }
                }
                if (e instanceof JedisDataException) {
                    if (e.getMessage().contains("ERR invalid password")) {
                        return Response.error("连接redis-server密码错误：" + e.getMessage());
                    }

                    if (e.getMessage().contains("NOAUTH Authentication required")) {
                        return Response.error("连接redis-server需要密码认证：" + e.getMessage());
                    }

                    if (e.getMessage().contains("ERR DB index is out of range")) {
                        // 查询数据库下标越界时 跳出循环
                        break;
                    }
                }

            }
        }

        // 关闭连接
        jedis.close();

        if (list.size() > 0) {
            return Response.success(list.size(), list);
        }

        // ERR DB index is out of range ：ERR数据库索引超出范围
        return Response.error("ERR数据库索引超出范围");
    }
}
