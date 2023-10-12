package com.base.test.project.business.service;

import com.base.test.project.business.domain.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {
	Page<User> pageList(Page<User> page,User user);

	Page<User> pageList();

	Long detail(Long id);

	Long add();

	void edit();

	void delete(Long[] ids);

	List<User> exportList();
}

