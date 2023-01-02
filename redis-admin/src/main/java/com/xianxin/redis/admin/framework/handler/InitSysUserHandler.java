package com.xianxin.redis.admin.framework.handler;

import com.xianxin.redis.admin.bean.enu.RoleType;
import com.xianxin.redis.admin.bean.po.SysUser;
import com.xianxin.redis.admin.dao.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitSysUserHandler implements ApplicationRunner {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public void run(ApplicationArguments args) {
        long count = sysUserRepository.count();
        log.info("检测系统用户数量：{}", count);
        if (count == 0) {
            log.info("开始初始化用户信息...");
            sysUserRepository.save(adminUser());
            sysUserRepository.save(devUser());
            log.info("初始化完成！");
        }
    }

    private SysUser adminUser() {
        SysUser sysUser = new SysUser();
        sysUser.setId("1");
        sysUser.setUsername("admin");
        sysUser.setNickname("系统管理员");
        sysUser.setAddr("坐标上海");
        sysUser.setPassword("admin");
        sysUser.setQq("1138645967");
        sysUser.setSex("男");
        sysUser.setRole(RoleType.ADMIN.name());
        sysUser.setStatus(Boolean.TRUE);
        sysUser.setAvatar("https://portrait.gitee.com/uploads/avatars/user/523/1571481_xianxin98_1580311321.png");
        log.info("Role:{} - username:{} - password:{}", sysUser.getRole(), sysUser.getUsername(), sysUser.getPassword());
        return sysUser;
    }

    private SysUser devUser() {
        SysUser sysUser = new SysUser();
        sysUser.setId("2");
        sysUser.setUsername("developer");
        sysUser.setNickname("贤心i");
        sysUser.setAddr("坐标上海");
        sysUser.setPassword("developer");
        sysUser.setQq("1138645967");
        sysUser.setSex("男");
        sysUser.setRole(RoleType.DEV.name());
        sysUser.setStatus(Boolean.TRUE);
        sysUser.setAvatar("https://portrait.gitee.com/uploads/avatars/user/523/1571481_xianxin98_1580311321.png");
        log.info("Role:{} - username:{} - password:{}", sysUser.getRole(), sysUser.getUsername(), sysUser.getPassword());
        return sysUser;
    }
}
