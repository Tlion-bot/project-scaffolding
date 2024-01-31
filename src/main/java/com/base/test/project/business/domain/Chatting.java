package com.base.test.project.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@TableName("chatting")
@Accessors(chain = true)
@Data
public class Chatting implements Serializable {
	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 发送者id
	 */
	@TableField
	private Long fid;
	/**
	 * 接收者id
	 */
	@TableField
	private Long tid;
	/**
	 * 消息
	 */
	@TableField
	private String message;
	/**
	 * 1文字 2表情 3图片
	 */
	@TableField
	private Integer type;
	/**
	 * 时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField
	private Date stime;
	/**
	 * 0未读 1已读
	 */
	@TableField
	private Integer status;


}

