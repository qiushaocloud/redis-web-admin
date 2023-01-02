package com.xianxin.redis.admin.dao;

import com.xianxin.redis.admin.bean.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, String> {

    SysUser findByUsername(String username);

}
