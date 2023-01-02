package com.xuebusi.cms.base.modules.redis.service;

import com.xuebusi.cms.base.modules.redis.dao.RedisClusterDao;
import com.xuebusi.cms.base.modules.redis.entity.RedisCluster;
import com.xuebusi.cms.common.persistence.Page;
import com.xuebusi.cms.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 集群配置Service
 *
 * @author shiyanjun
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class RedisClusterService extends CrudService<RedisClusterDao, RedisCluster> {

    public RedisCluster get(String id) {
        return super.get(id);
    }

    public List<RedisCluster> findList(RedisCluster redisCluster) {
        return super.findList(redisCluster);
    }

    public Page<RedisCluster> findPage(Page<RedisCluster> page, RedisCluster redisCluster) {
        return super.findPage(page, redisCluster);
    }

    @Transactional(readOnly = false)
    public void save(RedisCluster redisCluster) {
        super.save(redisCluster);
    }

    @Transactional(readOnly = false)
    public void delete(RedisCluster redisCluster) {
        super.delete(redisCluster);
    }

}