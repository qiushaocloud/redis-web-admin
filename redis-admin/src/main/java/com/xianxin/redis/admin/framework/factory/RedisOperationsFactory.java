package com.xianxin.redis.admin.framework.factory;

import com.xianxin.redis.admin.framework.common.ConSts;
import com.xianxin.redis.admin.framework.exception.ServerException;
import com.xianxin.redis.admin.service.RedisOperations;
import com.xianxin.redis.admin.service.impl.*;

public class RedisOperationsFactory {

    public static RedisOperations build(String type) {
        if (ConSts.STRIGN.equals(type)) {

            return new RedisOperationsForStringImpl();
        } else if (ConSts.LIST.equals(type)) {

            return new RedisOperationsForListImpl();
        } else if (ConSts.HASH.equals(type)) {

            return new RedisOperationsForHashImpl();
        } else if (ConSts.SET.equals(type)) {

            return new RedisOperationsForSetImpl();
        } else if (ConSts.ZSET.equals(type)) {

            return new RedisOperationsForZSetImpl();
        } else {
            throw new ServerException("非法的操作");
        }
    }
}
