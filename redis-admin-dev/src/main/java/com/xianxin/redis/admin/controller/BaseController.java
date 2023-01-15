package com.xianxin.redis.admin.controller;

import com.xianxin.redis.admin.bean.po.SysUser;
import com.xianxin.redis.admin.dao.SysUserRepository;
import com.xianxin.redis.admin.framework.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
//@CrossOrigin
public class BaseController {

    @Autowired
    private SysUserRepository sysUserRepository;

    public SysUser getLoginUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();
        String username = httpServletRequest.getHeader("x-username");
        SysUser sysUser = sysUserRepository.findByUsername(username);
        if (sysUser == null) {
            throw new ServerException("用户不存在");
        }
        if (Boolean.FALSE.equals(sysUser.getStatus())) {
            throw new ServerException("用户已停用");
        }
        return sysUser;
    }

}
