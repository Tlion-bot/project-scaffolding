package com.base.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.test.domain.Logintest;

import java.util.List;


/**
* @author Administrator
* @description 针对表【logintest】的数据库操作Service
* @createDate 2023-02-16 11:01:32
*/
public interface LogintestService extends IService<Logintest> {

    List<Logintest> selectAll();
}
