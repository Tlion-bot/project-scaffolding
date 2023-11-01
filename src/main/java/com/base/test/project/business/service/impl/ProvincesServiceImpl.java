package com.base.test.project.business.service.impl;

import com.base.test.project.business.domain.Provinces;

import com.base.test.project.business.mapper.ProvincesMapper;
import com.base.test.project.business.service.ProvincesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class ProvincesServiceImpl extends ServiceImpl<ProvincesMapper, Provinces> implements ProvincesService {
	@Autowired
	ProvincesMapper provincesMapper;
	@Override
	public Page<Provinces> pageList(Page<Provinces> page, Provinces provinces){
		return provincesMapper.pageList(page,provinces);
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
}

