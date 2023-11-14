package com.base.test.project.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.test.common.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@TableName("user")
@Accessors(chain = true)
@Data
public class User implements Serializable {
	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 
	 */
	@Excel(name = "登录名称")
	@TableField
	// @Pattern(regexp = "(\\d+(\\.\\d+)?)|(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\n)",
	// 		message = "为数字或者时间格式应为：yyyy-MM-dd HH:mm:ss")
	// @Pattern(regexp = "\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}|(\\d+(\\.\\d+)?)",
	// 		message = "为数字或者时间格式应为：yyyy-MM-dd HH:mm:ss")
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

