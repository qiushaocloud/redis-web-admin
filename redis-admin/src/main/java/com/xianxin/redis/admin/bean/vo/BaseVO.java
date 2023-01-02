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
public class BaseVO implements Serializable {
    /**
     * 主机
     */
    private String host = "127.0.0.1";

    private int port = 6379;

    /**
     * db
     */
    private String db = "0";
}
