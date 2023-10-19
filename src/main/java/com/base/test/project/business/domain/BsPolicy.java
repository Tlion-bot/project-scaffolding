package com.base.test.project.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

@TableName("bs_policy")
@Accessors(chain = true)
@Data
public class BsPolicy implements Serializable {
	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 标题
	 */
	@TableField
	private String title;
	/**
	 * 内容
	 */
	@TableField
	private String content;
	/**
	 * 附件
	 */
	@TableField
	private String annex;
	/**
	 * 创建者
	 */
	@TableField
	private String createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField
	private Date createTime;
	/**
	 * 更新者
	 */
	@TableField
	private String updateBy;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField
	private Date updateTime;
	/**
	 * 删除标志（0代表存在 1代表删除）
	 */
	@TableField
	private String delFlag;
}

