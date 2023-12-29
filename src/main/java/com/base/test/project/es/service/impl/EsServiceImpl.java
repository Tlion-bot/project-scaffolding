package com.base.test.project.es.service.impl;

import cn.hutool.core.util.IdUtil;
import com.base.test.project.es.domain.Document;
import com.base.test.project.es.mapper.DocumentMapper;
import com.base.test.project.es.service.EsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * EsManageServiceImpl
 *
 * @Author: cy.he
 * @Date: 2023-06-05 15:23
 * @Version: 1.0
 */
@Slf4j
@Service
public class EsServiceImpl implements EsService {

    @Resource
    @Lazy
    private DocumentMapper documentMapper;

    @Override
    public Integer insertES(int num) {

        List<Document> lis = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Document document = new Document();
            document.setId(IdUtil.randomUUID());
            document.setTitle("es生成");
            document.setContent("es生成es生成es生成es生成es生成es生成es生成");
            document.setCreator("zhangsan");

            lis.add(document);
        }
        return this.documentMapper.insertBatch(lis);
    }
}
