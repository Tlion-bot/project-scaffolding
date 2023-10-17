package com.base.test.project.system.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.test.common.constant.UserConstants;
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
     * 校验用户名称是否唯一
     *
     * @param user 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(SysUser user) {
        Long userId = Validator.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.baseMapper.selectOneUserByUserName(user.getUserName());
        // SysUser info = getOne(new LambdaQueryWrapper<SysUser>()
        //         .select(SysUser::getUserId, SysUser::getUserName)
        //         .eq(SysUser::getUserName, user.getUserName()).eq(SysUser::getTenantId,SecurityUtils.getLoginUser().getTenantId()).eq(SysUser::getDelFlag,"0").last("limit 1"));
        if (Validator.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = Validator.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = getOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserId, SysUser::getPhonenumber)
                .eq(SysUser::getPhonenumber, user.getPhonenumber()).eq(SysUser::getDelFlag,"0").last("limit 1"));
        if (Validator.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * @author nnc
     * @date 2023/3/22 16:28
     */
    @Override
    public boolean registerUser(SysUser user) {
        user.setCreateBy("admin");
        user.setUpdateBy("admin");
        user.setStatus("0");
        int row=baseMapper.insert(user);//创建人更新人需要获取，未登录报错

        return row>0;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */

}
