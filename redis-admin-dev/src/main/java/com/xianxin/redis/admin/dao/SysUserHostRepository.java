package com.xianxin.redis.admin.dao;

import com.xianxin.redis.admin.bean.po.SysUserHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserHostRepository extends JpaRepository<SysUserHost, String> {

    List<SysUserHost> findByUsername(String username);
}
