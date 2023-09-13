package com.base.test.project.business.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.common.util.ExcelUtil;
import com.base.test.project.business.domain.User;
import com.base.test.project.business.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	/**
	 * 分页列表
	 */
	@PostMapping("pageList")
	public AjaxResult<Page<User>> pageList(@RequestBody Page<User> page,@RequestBody User user) {
		return AjaxResult.success(userService.pageList(page,user));
	}

	/**
	 * 详情
	 */
	@GetMapping("/{id}")
	public AjaxResult<Long> detail(@PathVariable("id") Long id) {
		return AjaxResult.success(userService.detail(id));
	}

	/**
	 * 新增
	 */
	@PostMapping("add")
	public AjaxResult<Long> add(@RequestBody String username,String password) {
		return AjaxResult.success(userService.add());
	}

	/**
	 * 编辑
	 */
	@PostMapping("edit")
	public AjaxResult<Void> edit() {
		userService.edit();
		return AjaxResult.success();
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/{ids}")
	public AjaxResult<Void> delete(@PathVariable Long[] ids) {
		userService.delete(ids);
		return AjaxResult.success();
	}


	@GetMapping ("/export")
	@ResponseBody
	public AjaxResult export( HttpServletResponse response)
	{
		List<User> list = userService.exportList();
		System.out.println(list);
		ExcelUtil<User> util = new ExcelUtil<User>(User.class);
		return util.exportExcelNew(list, "用户数据",response);
	}

}

