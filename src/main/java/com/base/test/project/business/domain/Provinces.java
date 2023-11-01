package com.base.test.project.business.domain;

import lombok.Data;

import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("provinces")
@Accessors(chain = true)
@Data
public class Provinces implements Serializable {
	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 全称
	 */
	@TableField
	private String fullTitle;
	/**
	 * 热门城市
	 */
	@TableField
	private String hotCity;
	/**
	 * 编码
	 */
	@TableField
	private String orderIndex;
	/**
	 * 父节点
	 */
	@TableField
	private String parent;
	/**
	 * 名称
	 */
	@TableField
	private String title;
	/**
	 * 类型
	 */
	@TableField
	private String type;
}

