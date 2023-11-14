package com.base.test.project.business.domain.req;

import com.base.test.project.business.domain.User;
import lombok.Data;

@Data
    public class PageUser extends User{
    private long total;
    private long size;


    // getters and setters...
}
