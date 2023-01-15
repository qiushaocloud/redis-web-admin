package com.xianxin.redis.admin.bean.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class SelectDBIndexDTO implements Serializable {

    private String name;
    private String db;
}
