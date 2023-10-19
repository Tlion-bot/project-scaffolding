package com.base.test.project.business.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.project.business.service.BsPolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/bsPolicy")
public class BsPolicyController {

	private final BsPolicyService bsPolicyService;

	/**
	 * 分页列表
	 */
	@PostMapping("pageList")
	public AjaxResult<Page<Long>> pageList() {
		return AjaxResult.success(bsPolicyService.pageList());
	}

	/**
	 * 详情
	 */
	@GetMapping("/{id}")
	public AjaxResult<Long> detail(@PathVariable("id") Long id) {
		return AjaxResult.success(bsPolicyService.detail(id));
	}

	/**
	 * 新增
	 */
	@PostMapping("add")
	public AjaxResult<Long> add() {
		return AjaxResult.success(bsPolicyService.add());
	}

	/**
	 * 编辑
	 */
	@PostMapping("edit")
	public AjaxResult<Void> edit() {
		bsPolicyService.edit();
		return AjaxResult.success();
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/{ids}")
	public AjaxResult<Void> delete(@PathVariable Long[] ids) {
		bsPolicyService.delete(ids);
		return AjaxResult.success();
	}
}

