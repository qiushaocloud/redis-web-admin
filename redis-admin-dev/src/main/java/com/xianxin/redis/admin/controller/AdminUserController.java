package com.xianxin.redis.admin.controller;

import com.xianxin.redis.admin.bean.dto.SysUserDTO;
import com.xianxin.redis.admin.bean.po.SysUser;
import com.xianxin.redis.admin.bean.vo.LoginUserVO;
import com.xianxin.redis.admin.bean.vo.SysUserQueryVO;
import com.xianxin.redis.admin.framework.common.Response;
import com.xianxin.redis.admin.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
@RestController
@RequestMapping("/user")
public class AdminUserController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<SysUser> login(@RequestBody LoginUserVO vo) {

        return adminUserService.login(vo);
    }

    @GetMapping(path = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<SysUserDTO>> query() {

        return adminUserService.queryList(new SysUserQueryVO());
    }

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> save(@RequestBody SysUser user) {

        return adminUserService.saveOrUpdateUser(user);
    }

    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> delete(@RequestBody List<String> ids) {

        return adminUserService.deleteBatchUser(ids);
    }
}
