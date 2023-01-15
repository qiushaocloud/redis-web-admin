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
            log.info("初始化完成！");
        }
    }

    private SysUser adminUser() {
        SysUser sysUser = new SysUser();
        sysUser.setId("1");
        sysUser.setUsername("<RM_AUTH_USERNAME>");
        sysUser.setNickname("系统管理员");
        sysUser.setAddr("坐标上海");
        sysUser.setPassword("<RM_AUTH_PASSWORD>");
        sysUser.setQq("123456789");
        sysUser.setSex("男");
        sysUser.setRole(RoleType.ADMIN.name());
        sysUser.setStatus(Boolean.TRUE);
        sysUser.setAvatar("https://githubcdn.qiushaocloud.top/gh/qiushaocloud/cdn-static/blog/user_default.jpg");
        log.info("Role:{} - username:{} - password:{}", sysUser.getRole(), sysUser.getUsername(), sysUser.getPassword());
        return sysUser;
    }
}
