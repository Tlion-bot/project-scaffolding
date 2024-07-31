package com.base.test.java.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预约单(ReserveOrder)表实体类
 *
 * @author chenqingshan
 * @since 2024-05-10 16:28:03
 */
@TableName("b_reserve_order")
@Accessors(chain = true)
@Data
public class ReserveOrder extends Model<ReserveOrder> implements Serializable{
    //主键id
    @TableId(type = IdType.AUTO)
    private Long id;
    //球馆id
    private Long clubId;
    //球馆地址
    private String clubAddress;
    //球馆主图
    private String clubImage;
    //球桌类型
    private String ballTableType;
    //球桌单价
    private BigDecimal ballTableUnitPrice;
    //预约时段开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    //预约时段结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    //球桌订单金额
    private BigDecimal ballTableOrderPrice;
    //助教id
    private Long coachUserId;
    //助教名称
    private String coachName;
    //助教级别
    private String coachGrade;
    //助教价格
    private BigDecimal coachUnitPrice;
    //助教订单金额
    private BigDecimal coachOrderPrice;
    //预约人id
    private Long reserveUserId;
    //预约时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reserveTime;
    //助教预约状态0已取消1预约中2进行中3已完结
    private Integer reserveStatus;
    //球桌预约状态0已取消1预约中2进行中3已完结
    private Integer clubReserveStatus;
    //删除标志（0代表存在 1代表删除）
    private String delFlag;
    //创建者
    private String createBy;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //更新者
    private String updateBy;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
