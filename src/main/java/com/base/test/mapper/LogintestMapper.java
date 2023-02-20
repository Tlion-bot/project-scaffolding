package com.base.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.test.domain.Logintest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
* @author Administrator
* @description 针对表【logintest】的数据库操作Mapper
* @createDate 2023-02-16 11:01:32
* @Entity com.base.test.domain.Logintest
*/
@Mapper
public interface LogintestMapper extends BaseMapper<Logintest> {
List<Logintest> selectAll();

}
