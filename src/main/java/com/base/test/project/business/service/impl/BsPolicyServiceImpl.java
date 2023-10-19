package com.base.test.project.business.service.impl;

import com.base.test.project.business.domain.BsPolicy;

import com.base.test.project.business.mapper.BsPolicyMapper;
import com.base.test.project.business.service.BsPolicyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class BsPolicyServiceImpl extends ServiceImpl<BsPolicyMapper, BsPolicy> implements BsPolicyService {
	@Override
	public Page<Long> pageList() {
		return null;
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

