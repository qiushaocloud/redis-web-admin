package com.xianxin.redis.admin.bean.vo;

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
public class CacheRedisQueryVO extends BaseVO implements Serializable {

    private String keyword;

    private String cacheKeyword;

    /**
     * 数据类型
     */
    private String type;

    private int pageNo = 1;

    /**
     * 默认100条分页
     */
    private int pageSize = 100;
}
