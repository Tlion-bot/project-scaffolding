package com.base.test.project.business.service;

import com.base.test.project.business.domain.Provinces;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProvincesService extends IService<Provinces> {
	Page<Provinces> pageList(Page<Provinces> page,Provinces provinces);

	Long detail(Long id);

	Long add();

	void edit();

	void delete(Long[] ids);
}

