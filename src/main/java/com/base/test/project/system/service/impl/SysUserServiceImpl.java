package com.base.test.project.system.service.impl;

import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.mybatisplus.core.ServicePlusImpl;
import com.base.test.project.system.mapper.SysUserMapper;
import com.base.test.project.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户 业务层处理
 *
 * @author baseWork
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServicePlusImpl<SysUserMapper, SysUser> implements ISysUserService {


    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return baseMapper.selectUserByUserName(userName);
    }



    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */

}
