package com.base.test.project.mongoDB.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("stus")
public class Students {
    //String id;
   private String name;
    private String age;
    private String gender;
}
