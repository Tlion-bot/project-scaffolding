package com.base.test.project.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.test.common.enums.ActionTypeEnum;
import com.base.test.project.business.domain.User;
import com.base.test.project.business.domain.UserCopy;
import com.base.test.project.business.mapper.UserMapper;
import com.base.test.project.business.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	UserCopyServiceImpl userCopyService;
	@Override
	public Page<User> pageList(Page<User> page,User user) {
		return this.baseMapper.pageList(page,user);
	}

	@Override
	public Page<User> pageList() {
		return super.page(new Page<>(1, 10), Wrappers.lambdaQuery(User.class));
	}

	@Override
	public Long detail(Long id) {
		return null;
	}

	@Override
	public Long add() {
		return null;
	}

	@Override
	public void edit() {
	}

	@Override
	public void delete(Long[] ids) {
	}
	@Override
	public List<User> exportList(){
		return baseMapper.exportList();
	}

	@Override
	public void synchronization() {
		log.info("-------------定时任务：用户数据，开始-----------");
		try {
			Page<User> pageList = userService.pageList();
			List<User> userList=pageList.getRecords();


			//同步，有的更新没有的增加
			List<Long> userListAll = userService.list().stream().map(User::getId).collect(Collectors.toList());
			List<Long> userAlreadyExists = userCopyService.list().stream().map(UserCopy::getUserId).collect(Collectors.toList());

			if (userAlreadyExists.size() > 0) {
				List<User> userListUpdate = userService.listByIds(userAlreadyExists);
				userService.synchronizeData(userListUpdate, ActionTypeEnum.UPDATE);
			}else {
				log.info("无可更新用户数据");
			}
			if (userListAll.size()>0) {

				userListAll.removeAll(userAlreadyExists);
				if (userListAll.size()>0){
					List<User> userNeedAddList=userService.listByIds(userListAll);

					userService.synchronizeData(userNeedAddList, ActionTypeEnum.ADD);
				}else {
					log.info("无可增加用户数据");
				}
			}else {
				log.info("无可更新和可增加用户数据");
			}
			log.info("-------------定时任务：用户数据，结束-----------");
		} catch (Exception e) {
			log.error("定时任务：同步租户或用户数据，出现错误", e);
		}
	}

	public void synchronizeData(List<User>userList,ActionTypeEnum actionType){
		for (User user:userList){

			if (ActionTypeEnum.ADD.equals(actionType)) {
				UserCopy userCopy=BeanUtil.copyProperties(user,UserCopy.class);
				userCopy.setActionType(actionType.code);
				userCopy.setUserId(user.getId());
				userCopyService.save(userCopy);
			}else {
				UserCopy userCopy=userCopyService.getOne(Wrappers.lambdaQuery(UserCopy.class)
						.eq(UserCopy::getUserId, user.getId()));
				userCopy.setActionType(actionType.code);
				userCopyService.updateById(userCopy);
			}
		}

	}
}

