package com.xianxin.redis.admin.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/02/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CacheSynchVO extends BaseVO {

    private String keyword;

    /**
     * 数据类型
     */
    private String type;

    /**
     * 目标主机
     */
    private String targetHost;

    private int targetPort = 6379;
    /**
     * 目标db
     */
    private String targetDb;

    private int pageNo = 1;
}
