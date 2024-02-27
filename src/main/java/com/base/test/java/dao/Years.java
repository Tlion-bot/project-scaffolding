package com.base.test.java.dao;

import lombok.Data;

@Data
public class Years {
    private int cyear;  // 存储年份
    private int cperiod;  // 存储月份

    public Years(int cyear, int cperiod) {
        this.cyear = cyear;
        this.cperiod = cperiod;
    }
}
