package com.base.test.project.business.domain;

import com.base.test.common.annotation.Excel;
import lombok.Data;

import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("user")
@Accessors(chain = true)
@Data
public class User implements Serializable {
	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 
	 */
	@Excel(name = "登录名称")
	@TableField
	private String name;
	/**
	 * 
	 */
	@TableField
	private String password;
	/**
	 * 
	 */
	@Excel(name = "性别")
	@TableField
	private String sex;
	/**
	 * 
	 */
	@Excel(name = "手机号")
	@TableField
	private String phone;
	/**
	 * 
	 */
	@TableField
	private Integer deptId;
	/**
	 * 
	 */
	@TableField
	private String f;
	/**
	 * 
	 */
	@TableField
	private String t;
}

