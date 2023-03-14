package com.base.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.test.domain.Logintest;
import com.base.test.mapper.LogintestMapper;
import com.base.test.service.LogintestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【logintest】的数据库操作Service实现
* @createDate 2023-02-16 11:01:32
*/
@Service
public class LogintestServiceImpl extends ServiceImpl<LogintestMapper, Logintest>
implements LogintestService{
    @Autowired
    LogintestMapper logintestMapper;
    @Override
    public List<Logintest> selectAll(){
        //aaaaaaa


      Logintest logintest=new Logintest();

        return logintestMapper.selectAll();
    }
}
