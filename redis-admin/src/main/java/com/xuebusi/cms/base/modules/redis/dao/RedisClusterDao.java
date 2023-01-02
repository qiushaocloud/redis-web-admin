package com.xuebusi.cms.base.modules.redis.dao;

import com.xuebusi.cms.base.modules.redis.entity.RedisCluster;
import com.xuebusi.cms.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 集群配置DAO接口
 *
 * @author shiyanjun
 * @version 2018-11-14
 */
@Mapper
public interface RedisClusterDao extends CrudDao<RedisCluster> {

}