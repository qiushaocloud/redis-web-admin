package com.xianxin.redis.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.xianxin.redis.admin.bean.dto.SysUserDTO;
import com.xianxin.redis.admin.bean.enu.RoleType;
import com.xianxin.redis.admin.bean.po.SysUser;
import com.xianxin.redis.admin.bean.po.SysUserHost;
import com.xianxin.redis.admin.bean.vo.LoginUserVO;
import com.xianxin.redis.admin.bean.vo.SysUserQueryVO;
import com.xianxin.redis.admin.dao.SysUserHostRepository;
import com.xianxin.redis.admin.dao.SysUserRepository;
import com.xianxin.redis.admin.framework.common.Response;
import com.xianxin.redis.admin.framework.exception.ServerException;
import com.xianxin.redis.admin.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserHostRepository sysUserHostRepository;

    @Override
    public Response<SysUser> login(LoginUserVO loginUser) {
        SysUser entity = sysUserRepository.findByUsername(loginUser.getUsername());
        if (entity == null) {
            return Response.error("用户不存在");
        }
        if (!entity.getPassword().equals(loginUser.getPassword())) {
            return Response.error("密码错误");
        }
        if (entity.getStatus().equals(Boolean.FALSE)) {
            return Response.error("用户已停用");
        }

        return Response.success(entity);
    }

    @Override
    public Response<Boolean> saveOrUpdateUser(SysUser user) {

        SysUser entity = sysUserRepository.findByUsername(user.getUsername());

        if (entity != null && entity.getUsername().equals(user.getUsername()) && !user.getId().equals(entity.getId())) {
            return Response.error("用户已存在");
        }

        //生成ID
        if (entity == null && StringUtils.isBlank(user.getId())) {
            long nextId = IdUtil.createSnowflake(0, 0).nextId();
            user.setId("U" + nextId);
        }

        if ("admin".equals(user.getUsername()) && RoleType.ADMIN.name().equals(user.getRole())) {
            return Response.error("管理员用户不能停用");
        }

        sysUserRepository.save(user);

        sysUserHostRepository.findByUsername(user.getUsername()).forEach(item -> sysUserHostRepository.deleteById(item.getId()));

        //保存可用的redis
        List<String> redisServers = user.getHosts();
        if (CollectionUtil.isNotEmpty(redisServers)) {
            for (String redisServer : redisServers) {
                String[] split = redisServer.split(":");
                sysUserHostRepository.save(new SysUserHost(user.getUsername(), split[0], Integer.parseInt(split[1])));
            }
        }

        return Response.success(200, "保存成功", Boolean.TRUE);
    }

    @Override
    public Response<Boolean> enabledOrDisabledUser(SysUser user) {
        if (user.getId() == null) {
            return Response.error("用户Id不能为空");
        }

        Optional<SysUser> userOptional = sysUserRepository.findById(user.getId());
        if (!userOptional.isPresent()) {
            return Response.error("用户不存在");
        }

        SysUser entity = userOptional.get();

        if ("admin".equals(entity.getUsername()) && RoleType.ADMIN.name().equals(entity.getRole())) {
            return Response.error("管理员用户不能停用");
        }
        entity.setStatus(user.getStatus());

        sysUserRepository.save(entity);

        return Response.success(200, "操作成功", Boolean.TRUE);
    }

    @Override
    public Response<Boolean> deleteBatchUser(List<String> ids) {

        for (String id : ids) {
            Optional<SysUser> userOptional = sysUserRepository.findById(id);
            if (userOptional.isPresent()) {
                SysUser sysUser = userOptional.get();
                if (RoleType.ADMIN.name().equals(sysUser.getRole()) && "admin".equals(sysUser.getUsername())) {
                    throw new ServerException("管理员用户不能删除");
                } else {
                    sysUserRepository.deleteById(id);
                }
            }
        }

        return Response.success(Boolean.TRUE);
    }

    @Override
    public Response<List<SysUserDTO>> queryList(SysUserQueryVO vo) {

        List<SysUserDTO> dtoList = new ArrayList<>();

        List<SysUser> all = sysUserRepository.findAll();
        for (SysUser sysUser : all) {
            //查询用户关联的主机
            List<SysUserHost> sysUserHostList = sysUserHostRepository.findByUsername(sysUser.getUsername());
            List<String> hosts = new ArrayList<>();
            for (SysUserHost sysUserHost : sysUserHostList) {
                hosts.add(sysUserHost.getHost() + ":" + sysUserHost.getPort());
            }
            sysUser.setHosts(hosts);

            SysUserDTO dto = new SysUserDTO();
            BeanUtil.copyProperties(sysUser, dto);

            dtoList.add(dto);
        }

        return Response.success(dtoList);
    }

    @Override
    public Response<SysUser> queryDetails(SysUserQueryVO vo) {

        Optional<SysUser> optional = sysUserRepository.findById(vo.getId());

        return optional.map(Response::success).orElseGet(() -> Response.error("用户不存在"));
    }
}
