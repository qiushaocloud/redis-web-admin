package com.xianxin.redis.admin.dao;

import com.xianxin.redis.admin.bean.po.RedisConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedisConfigRepository extends JpaRepository<RedisConfig, String> {

    List<RedisConfig> findByHost(String host);

    RedisConfig findByHostAndPort(String host, int port);
}
