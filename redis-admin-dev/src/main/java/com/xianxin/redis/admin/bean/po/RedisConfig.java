package com.xianxin.redis.admin.bean.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class RedisConfig implements Serializable {

    @Id
    private String id;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 密码
     */
    private String password;

    /**
     * 别名
     */
    private String name;

    /**
     * 可用状态
     */
    private Boolean status = false;

    /**
     * 环境
     */
    private String env;
}
