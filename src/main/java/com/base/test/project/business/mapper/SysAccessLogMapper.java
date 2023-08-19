package com.base.test.project.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.test.project.business.domain.SysAccessLog;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Author chenqingshan
 * @Date 2023/7/5 13:55
 * @Description TODO
 **/
@Mapper
public interface SysAccessLogMapper extends BaseMapper<SysAccessLog> {
    void insertSysAccessLog(SysAccessLog sysAccessLog);
}
