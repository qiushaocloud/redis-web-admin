package com.xianxin.redis.admin.controller;

import com.xianxin.redis.admin.bean.dto.RedisConfigDTO;
import com.xianxin.redis.admin.bean.dto.SelectDBIndexDTO;
import com.xianxin.redis.admin.bean.dto.SysRedisDTO;
import com.xianxin.redis.admin.bean.po.RedisConfig;
import com.xianxin.redis.admin.bean.po.SysRedis;
import com.xianxin.redis.admin.bean.vo.*;
import com.xianxin.redis.admin.framework.annotation.LogAnnotation;
import com.xianxin.redis.admin.framework.common.Response;
import com.xianxin.redis.admin.service.RedisManagerService;
import com.xianxin.redis.admin.service.RedisOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
@RestController
@RequestMapping(path = "/redis")
public class RedisController extends BaseController {

    @Autowired
    private RedisManagerService redisManagerService;

    @Autowired
    private RedisOperationService redisOperationService;

    @LogAnnotation(module = "配置", business = "列表查询")
    @GetMapping(path = "/config/query")
    public Response<List<RedisConfig>> queryConfig() {

        return redisManagerService.list();
    }

    @LogAnnotation(module = "缓存", business = "列表查询")
    @PostMapping(path = "/cache/query")
    public Response<List<SysRedis>> queryCache(@RequestBody CacheRedisQueryVO vo) {

        return redisOperationService.cacheList(vo);
    }

    @LogAnnotation(module = "缓存", business = "详情查询")
    @PostMapping(path = "/cache/details")
    public Response<SysRedisDTO> queryCacheDetails(@RequestBody CacheRedisQueryVO vo) {

        return redisOperationService.cacheDetails(vo);
    }

    @LogAnnotation(module = "缓存", business = "同步")
    @PostMapping(path = "/cache/synch")
    public Response<Boolean> cacheSynch(@RequestBody CacheSynchVO vo) {

        return redisOperationService.cacheSynch(vo);
    }

    @LogAnnotation(module = "缓存", business = "发布")
    @PostMapping(path = "/cache/publish")
    public Response<Long> cachePublish(@RequestBody CachPublishVO vo) {

        return redisOperationService.cachePublish(vo);
    }

    @LogAnnotation(module = "缓存", business = "创建缓存")
    @PostMapping(path = "/cache/create")
    public Response<Boolean> cacheCreate(@RequestBody CacheRedisVO vo) {

        return redisOperationService.cacheCreate(vo);
    }

    @LogAnnotation(module = "缓存", business = "更新缓存名称")
    @PostMapping(path = "/cache/name/update")
    public Response<Boolean> cacheNameUpdate(@RequestBody CacheRedisVO vo) {

        return redisOperationService.cacheNameUpdate(vo);
    }

    @LogAnnotation(module = "缓存", business = "更新缓存失效时间")
    @PostMapping(path = "/cache/expire/update")
    public Response<Boolean> cacheExpireUpdate(@RequestBody CacheRedisVO vo) {

        return redisOperationService.cacheExpireUpdate(vo);
    }

    @LogAnnotation(module = "缓存", business = "创建缓存值")
    @PostMapping(path = "/cache/value/create")
    public Response<Boolean> cacheValueCreate(@RequestBody CacheRedisVO vo) {

        return redisOperationService.cacheValueCreate(vo);
    }

    @LogAnnotation(module = "缓存", business = "更新缓存值")
    @PostMapping(path = "/cache/value/update")
    public Response<Boolean> cacheValueUpdate(@RequestBody CacheRedisVO vo) {

        return redisOperationService.cacheValueUpdate(vo);
    }

    @LogAnnotation(module = "缓存", business = "删除缓存")
    @PostMapping(path = "/cache/delete")
    public Response<Boolean> cacheDelete(@RequestBody CacheRedisVO vo) {

        return redisOperationService.cacheDelete(vo);
    }

//    @LogAnnotation(module = "缓存", business = "缓存")
//    @GetMapping(path = "/config/select")
//    public Response<List<Map<String, String>>> select() {
//        return sysRedisService.select();
//    }

    @LogAnnotation(module = "配置", business = "创建配置")
    @PostMapping(path = "/config/create")
    public Response<Boolean> create(@RequestBody RedisConfig vo) {

        return redisManagerService.saveOrUpdateConfig(vo);
    }

    @LogAnnotation(module = "配置", business = "更新配置")
    @PostMapping(path = "/config/update")
    public Response<Boolean> update(@RequestBody RedisConfig vo) {

        return redisManagerService.saveOrUpdateConfig(vo);
    }

    @LogAnnotation(module = "配置", business = "启用配置")
    @GetMapping(path = "/config/enabled")
    public Response<Boolean> enabled(BaseVO vo) {

        return redisManagerService.enabledOrDisabled(vo.getHost(), vo.getPort(), true);
    }

    @LogAnnotation(module = "配置", business = "停用配置")
    @GetMapping(path = "/config/disabled")
    public Response<Boolean> disabled(BaseVO vo) {

        return redisManagerService.enabledOrDisabled(vo.getHost(), vo.getPort(), false);
    }

    @LogAnnotation(module = "配置", business = "删除配置")
    @GetMapping(path = "/config/delete")
    public Response<Boolean> delete(BaseVO vo) {

        return redisManagerService.deleteConfig(vo.getHost(), vo.getPort());
    }

    @LogAnnotation(module = "配置", business = "测试连接")
    @PostMapping(path = "/config/test/conn")
    public Response<Void> connection(@RequestBody RedisConfig config) {
        try {
            Jedis jedis = redisManagerService.getJedis(config, "0");
            String ping = jedis.ping();
//            jedis.hset("test_connection", config.getHost(), "success");
            jedis.close();
            return Response.success("redis-server连接成功");
        } catch (Exception e) {
//            if(e instanceof JedisDataException){
//                //哨兵模式时 从节点写操作就会报这个异常
//                if("READONLY You can't write against a read only slave.".equals(e.getMessage())){
//                }
//            }
            return Response.error("redis-server连接出现异常：" + e.getMessage());
        }
    }

    @LogAnnotation(module = "缓存", business = "缓存")
    @GetMapping(path = "/config/select")
    public Response<List<RedisConfigDTO>> select() {
//        SysUser sysUser = new SysUser();
//        sysUser.setUsername("admin");
//        sysUser.setRole(RoleType.ADMIN.name());

        return redisManagerService.select(super.getLoginUser());
    }

    @LogAnnotation(module = "配置", business = "获取DB")
    @GetMapping(path = "/config/select/db")
    public Response<List<SelectDBIndexDTO>> selectDb(BaseVO vo) {

        return redisManagerService.selectDBIndex(vo.getHost(), vo.getPort());
    }

//    @LogAnnotation(module = "配置", business = "消息订阅")
//    @PostMapping(path = "/psubscribe")
//    public Response psubscribe(@RequestBody HookCommonVo vo) {
//        return sysRedisService.psubscribe(vo);
//    }
//
//    @LogAnnotation(module = "配置", business = "取消订阅")
//    @PostMapping(path = "/punsubscribe")
//    public Response punsubscribe(@RequestBody HookCommonVo vo) {
//        return sysRedisService.punsubscribe(vo);
//    }
//
//    @GetMapping(path = "/enabled/hook")
//    public Response enabledHook(String host) {
//        return sysRedisService.enabledHook(host);
//    }
//
//    @GetMapping(path = "/disabled/hook")
//    public Response disabledHook(String host) {
//        return sysRedisService.disabledHook(host);
//    }
}
