package com.base.test.project.business.domain;

import lombok.Data;

import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("user_copy")
@Accessors(chain = true)
@Data
public class UserCopy implements Serializable {
	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 
	 */
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
	@TableField
	private String sex;
	/**
	 * 
	 */
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

