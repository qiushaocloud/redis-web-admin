package com.xianxin.redis.admin;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Student {
    @Id
    private Integer id;
    private Integer age;
    private String name;
    private String address;
    private String city;

}
