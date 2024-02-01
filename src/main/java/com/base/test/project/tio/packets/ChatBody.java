
package com.base.test.project.tio.packets;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "ChatBody", description = "沟通信息")
public class ChatBody implements Serializable {

	private String id;
	/**
	 * 发送用户id;
	 */
	private String from;
	/**
	 * 目标用户id;
	 */
	private String to;

	@ApiModelProperty(value ="消息类型 0:text、1:image、2:voice、3:vedio、4:music、5:news 6 面试预约 7 简历 8 撤销")
	private Integer msgType;
	/**
	 * 命令;(0 普通消息   6 撤销 7 接受 8 拒绝 )
	 */
	@ApiModelProperty(value ="命令 0 普通消息无操作  6 撤销 7 接受 8 拒绝 ")
	private Integer cmd = 0;

	private String fileType;
	private String filePath;
	private String fileName;
	/**
	 * 聊天类型;(如 2 公聊、1 私聊)
	 */
	@ApiModelProperty(value ="聊天类型 2 公聊、1 私聊")
	private Integer chatType;
	/**
	 * 消息内容;
	 */
	private String content;
	/**
	 * 消息发到哪个群组;
	 */
	private String groupId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;

	private String pId;
	private String interviewId;
	@ApiModelProperty(value ="请求方来源  web/app")
	private String source;
	private String status;
}
