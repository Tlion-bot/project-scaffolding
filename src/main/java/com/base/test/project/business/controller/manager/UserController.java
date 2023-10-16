package com.base.test.project.business.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.test.common.core.domain.AjaxResult;
import com.base.test.common.util.ExcelUtil;
import com.base.test.project.business.domain.User;
import com.base.test.project.business.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		// public AjaxResult<Page<User>> pageList() {
		//  Page<User> page = new Page<User>(); User user=null;
		//  page.setSize(1);
		//  page.setTotal(10);
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
	public AjaxResult add(@RequestParam String username,@RequestParam String password) {
		User user=new User();
		user.setName(username);
		user.setPassword(password);
		userService.save(user);
		return AjaxResult.success();
	}

	/**
	 * 编辑
	 */
	@PostMapping("edit")
	public void edit() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet("http://localhost:8089/user/pageList");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();

			if (httpEntity != null) {
				String responseString = EntityUtils.toString(httpEntity);
				System.out.println(responseString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * 编辑
	 */
	@PostMapping("get")
	public void get() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet("http://localhost:8089/user/pageList");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();

			if (httpEntity != null) {
				String responseString = EntityUtils.toString(httpEntity);
				System.out.println(responseString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 编辑
	 */
	@PostMapping("post")
	public void post() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			//设置请求方式和地址
			HttpPost httpPost = new HttpPost("http://localhost:8089/user/pageList");
			//设置请求体
			ContentType contentType= ContentType.create("application/json", "utf-8");//设置编码格式
			StringEntity entity = new StringEntity("{\"size\":1}",
					contentType);
			httpPost.setEntity(entity);

			//设置请求头
			// httpPost.setHeader("Authorization", "Bearer " + "your_token");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity responseHttpEntity = httpResponse.getEntity();

			if (responseHttpEntity != null) {
				String responseString = EntityUtils.toString(responseHttpEntity);
				System.out.println(responseString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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

