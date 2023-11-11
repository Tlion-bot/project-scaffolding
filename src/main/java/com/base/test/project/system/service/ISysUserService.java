package com.base.test.project.system.service;

import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.core.mybatisplus.core.IServicePlus;

/**
 * 用户 业务层
 *
 * @author baseWork
 */
public interface ISysUserService extends IServicePlus<SysUser> {


    public boolean registerUser(SysUser user);

    public SysUser selectUserByUserName(String userName);

    public SysUser selectUserByEmail(String email);

    /**
     * 校验用户名称是否唯一
     *
     * @return 结果
     */
    public String checkUserNameUnique(SysUser user);


    public String checkPhoneUnique(SysUser user);
}
