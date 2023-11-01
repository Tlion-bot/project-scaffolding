package com.base.test.project.business.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.project.business.domain.Provinces;
import com.base.test.project.business.service.ProvincesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/provinces")
public class ProvincesController {

	private final ProvincesService provincesService;

	/**
	 * 分页列表
	 */
	@PostMapping("pageList")
	public AjaxResult<Page<Provinces>> pageList(@RequestBody Page<Provinces>page,Provinces provinces) {
		return AjaxResult.success(provincesService.pageList(page,provinces));
	}

	/**
	 * 详情
	 */
	@GetMapping("/{id}")
	public AjaxResult<Long> detail(@PathVariable("id") Long id) {
		return AjaxResult.success(provincesService.detail(id));
	}

	/**
	 * 新增
	 */
	@PostMapping("add")
	public AjaxResult<Long> add() {
		return AjaxResult.success(provincesService.add());
	}

	/**
	 * 编辑
	 */
	@PostMapping("edit")
	public AjaxResult<Void> edit() {
		provincesService.edit();
		return AjaxResult.success();
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/{ids}")
	public AjaxResult<Void> delete(@PathVariable Long[] ids) {
		provincesService.delete(ids);
		return AjaxResult.success();
	}
}

