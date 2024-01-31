package com.base.test.project.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.test.project.business.domain.Chatting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChattingMapper extends BaseMapper<Chatting> {
}

