package com.base.test.common.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExcelData implements Serializable{

    //文件名称
    private String fileName;
    //表头数据
    private String[] head;
    //数据
    private List<String[]> data;

}