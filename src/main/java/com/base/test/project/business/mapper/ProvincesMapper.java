package com.base.test.project.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.test.project.business.domain.Provinces;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProvincesMapper extends BaseMapper<Provinces> {
    Page<Provinces> pageList(@Param("page") Page<Provinces> page, @Param("param") Provinces param);
}

