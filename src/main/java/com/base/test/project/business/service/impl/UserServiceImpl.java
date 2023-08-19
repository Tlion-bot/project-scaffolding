package com.base.test.project.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.test.project.business.domain.User;
import com.base.test.project.business.mapper.UserMapper;
import com.base.test.project.business.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	@Autowired
	UserMapper userMapper;
	@Override
	public Page<User> pageList(Page<User> page,User user) {
		return this.baseMapper.pageList(page,user);
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
}

