package com.base.test.project.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.mybatisplus.core.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author baseWork
 */
public interface SysUserMapper extends BaseMapperPlus<SysUser> {

    Page<SysUser> selectPageUserList(@Param("page") Page<SysUser> page, @Param("user") SysUser user);

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    public SysUser selectUserByEmail(String email);
    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);


    SysUser selectOneUserByUserName(String userName);
}
