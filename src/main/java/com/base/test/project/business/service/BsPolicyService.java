package com.base.test.project.business.service;

import com.base.test.project.business.domain.BsPolicy;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BsPolicyService extends IService<BsPolicy> {
	Page<Long> pageList();

	Long detail(Long id);

	Long add();

	void edit();

	void delete(Long[] ids);
}

