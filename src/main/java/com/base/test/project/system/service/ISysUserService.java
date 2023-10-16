package com.base.test.project.system.service;

import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.core.mybatisplus.core.IServicePlus;

/**
 * 用户 业务层
 *
 * @author baseWork
 */
public interface ISysUserService extends IServicePlus<SysUser> {




    public SysUser selectUserByUserName(String userName);


}
