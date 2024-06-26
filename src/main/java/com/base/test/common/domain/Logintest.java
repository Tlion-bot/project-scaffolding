package com.base.test.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @TableName logintest
 */
@Data
@TableName(value ="login-test")
public class Logintest implements Serializable {
    /**
     * 
     */
    private int id;
    private String username;
    /**
     * 
     */
    private String password;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Logintest{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
 * TODO aa
 *  
 * @return long
 * @date 2023/3/9 16:10       
 */

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}