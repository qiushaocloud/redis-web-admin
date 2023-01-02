package com.xianxin.redis.admin.service;

import com.xianxin.redis.admin.bean.dto.SysUserDTO;
import com.xianxin.redis.admin.bean.po.SysUser;
import com.xianxin.redis.admin.bean.vo.LoginUserVO;
import com.xianxin.redis.admin.bean.vo.SysUserQueryVO;
import com.xianxin.redis.admin.framework.common.Response;

import java.util.List;

/**
 * Redis Admin 用户相关
 *
 * @author 贤心i
 * @email 1138645967@qq.com
 * @date 2020/01/29
 */
public interface AdminUserService {

    /**
     * 用户登录
     *
     * @param loginUser 登录用户
     * @return 登录成功后的用户系你行
     */
    Response<SysUser> login(LoginUserVO loginUser);

    /**
     * 保存或修改用户信息
     *
     * @param user 用户信息
     * @return 是否操作成功
     */
    Response<Boolean> saveOrUpdateUser(SysUser user);

    /**
     * 保存或修改用户信息
     *
     * @param user 用户信息
     * @return 是否操作成功
     */
    Response<Boolean> enabledOrDisabledUser(SysUser user);

    /**
     * 删除用户
     *
     * @param ids 用户唯一ID集合
     * @return 是否操作成功
     */
    Response<Boolean> deleteBatchUser(List<String> ids);

    /**
     * 查询用户列表（分页）
     *
     * @param vo 查询参数
     * @return 用户列表
     */
    Response<List<SysUserDTO>> queryList(SysUserQueryVO vo);

    /**
     * 查询用户详情
     *
     * @param vo 查询参数
     * @return 用户详情
     */
    Response<SysUser> queryDetails(SysUserQueryVO vo);
}
