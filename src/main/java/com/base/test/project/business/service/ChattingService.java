package com.base.test.project.business.service;

import com.base.test.project.business.domain.Chatting;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ChattingService extends IService<Chatting> {
	Page<Long> pageList();

	Long detail(Long id);

	Long add();

	void edit();

	void delete(Long[] ids);
}

