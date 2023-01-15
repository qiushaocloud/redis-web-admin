package com.xianxin.redis.admin.service;

import com.xianxin.redis.admin.bean.dto.SysRedisDTO;
import com.xianxin.redis.admin.bean.po.SysRedis;
import com.xianxin.redis.admin.bean.vo.CachPublishVO;
import com.xianxin.redis.admin.bean.vo.CacheRedisQueryVO;
import com.xianxin.redis.admin.bean.vo.CacheRedisVO;
import com.xianxin.redis.admin.bean.vo.CacheSynchVO;
import com.xianxin.redis.admin.framework.common.Response;

import java.util.List;

/**
 * Redis CRUD操作 service
 *
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
public interface RedisOperationService {

    /**
     * 缓存 - 列表查询
     *
     * @param vo 查询参数
     * @return 缓存列表
     */
    Response<List<SysRedis>> cacheList(CacheRedisQueryVO vo);

    /**
     * 缓存 - 详情
     *
     * @param vo 参数
     * @return 缓存详情
     */
    Response<SysRedisDTO> cacheDetails(CacheRedisQueryVO vo);

    /**
     * 缓存 - 创建
     *
     * @param vo 参数
     * @return 是否缓存成功
     */
    Response<Boolean> cacheCreate(CacheRedisVO vo);

    /**
     * 缓存 - 删除
     *
     * @param vo 参数
     * @return 是否成功
     */
    Response<Boolean> cacheDelete(CacheRedisVO vo);

    /**
     * 缓存 - 更新名称
     *
     * @param vo 参数
     * @return 是否成功
     */
    Response<Boolean> cacheNameUpdate(CacheRedisVO vo);

    /**
     * 缓存 - 创建缓存值
     *
     * @param vo 参数
     * @return 是否成功
     */
    Response<Boolean> cacheValueCreate(CacheRedisVO vo);

    /**
     * 缓存 - 更新缓存值
     *
     * @param vo 参数
     * @return 是否成功
     */
    Response<Boolean> cacheValueUpdate(CacheRedisVO vo);

    /**
     * 更新过期时间
     *
     * @param vo 参数
     * @return 是否成功
     */
    Response<Boolean> cacheExpireUpdate(CacheRedisVO vo);

    Response<Boolean> cacheSynch(CacheSynchVO vo);

    Response<Long> cachePublish(CachPublishVO vo);

}
