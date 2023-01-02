package com.xianxin.redis.admin;

import com.alibaba.fastjson.JSON;
import com.xianxin.redis.admin.bean.enu.EnvType;
import com.xianxin.redis.admin.bean.enu.RoleType;
import com.xianxin.redis.admin.bean.po.RedisConfig;
import com.xianxin.redis.admin.bean.po.SysUser;
import com.xianxin.redis.admin.dao.SysUserHostRepository;
import com.xianxin.redis.admin.dao.SysUserRepository;
import com.xianxin.redis.admin.framework.common.Response;
import com.xianxin.redis.admin.service.AdminUserService;
import com.xianxin.redis.admin.service.RedisManagerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Slf4j
@SpringBootTest
public class RedisServerApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private RedisManagerService redisManagerService;

    @Autowired
    private SysUserHostRepository sysUserHostRepository;

    @Test
    public void deleteSysUserHost() {
        sysUserHostRepository.deleteAll();

//        sysUserHostRepository.save(new SysUserHost("admin","10.25.24.1"));
    }

    @Test
    public void selectDBIndex() {
        log.info("{}", JSON.toJSONString(redisManagerService.selectDBIndex("10.25.24.1",6379)));
    }

    @Test
    public void list() {
        log.info("{}", JSON.toJSONString(redisManagerService.list()));
    }

    @Test
    public void deleteConfig() {
        Response<Boolean> booleanResponse = redisManagerService.deleteConfig("127.0.0.3",6379);
        log.info("{}", JSON.toJSONString(booleanResponse));
    }

    @Test
    public void enabledOrDisabled() {
        Response<Boolean> booleanResponse = redisManagerService.enabledOrDisabled("127.0.0.1",6379, Boolean.TRUE);
        log.info("{}", JSON.toJSONString(booleanResponse));
    }

    @Test
    public void saveOrUpdateConfig() {
        RedisConfig redisConfig = new RedisConfig();

//        redisConfig.setHost("127.0.0.1");
//        redisConfig.setHost("127.0.0.2");
//        redisConfig.setHost("127.0.0.3");
//        redisConfig.setHost("127.0.0.4");
//        redisConfig.setId("RC1516964368942628864");
        redisConfig.setName("测试");
        redisConfig.setPort(6379);
        redisConfig.setPassword("123456");
//        redisConfig.setPassword("");
        redisConfig.setEnv(EnvType.PROD.name());

        Response<Boolean> booleanResponse = redisManagerService.saveOrUpdateConfig(redisConfig);
        log.info("{}", JSON.toJSONString(booleanResponse));
    }

    @Test
    public void findByUsername() {
        SysUser sysUser = sysUserRepository.findByUsername("admin");
        System.out.println(sysUser.toString());
    }

    @Test
    public void deleteBatchUser() {
        adminUserService.deleteBatchUser(Arrays.asList("U1516712640729055232"));
    }

    @Test
    public void saveOrUpdateUser() {
        SysUser sysUser = new SysUser();

        sysUser.setId("1");
        sysUser.setUsername("admin");
        sysUser.setNickname("系统管理员");
        sysUser.setAddr("坐标上海");
        sysUser.setPassword("123456");
        sysUser.setQq("1138645967");
        sysUser.setSex("男");
        sysUser.setTel("15522886118");
        sysUser.setRole(RoleType.ADMIN.name());
//        sysUser.setRole(RoleType.DEV.name());
        sysUser.setStatus(Boolean.TRUE);

        sysUser.setAvatar("https://portrait.gitee.com/uploads/avatars/user/523/1571481_xianxin98_1580311321.png");

        sysUser.setHosts(Arrays.asList("127.0.0.1", "47.0.0.1"));

        Response<Boolean> booleanResponse = adminUserService.saveOrUpdateUser(sysUser);
        log.info("{}", JSON.toJSONString(booleanResponse));
    }

    @Test
    public void enabledOrDisabledUser() {
        SysUser sysUser = new SysUser();

        sysUser.setId("1");
        sysUser.setStatus(Boolean.TRUE);

        adminUserService.enabledOrDisabledUser(sysUser);
    }

    @Test
    public void addUser() {
        Student user = new Student();
        user.setId(1);
        user.setName("张三");
        user.setAddress("北京");
        user.setAge(19);
        user.setCity("北京");
        studentRepository.save(user);
    }

}
