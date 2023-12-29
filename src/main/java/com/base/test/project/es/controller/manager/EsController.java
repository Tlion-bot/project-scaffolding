package com.base.test.project.es.controller.manager;

import com.base.test.common.core.domain.AjaxResult;
import com.base.test.project.es.service.EsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private EsService esService;
    /**
     * 分页列表
     */
    @GetMapping("createDoc")
    public AjaxResult<Integer> pageList(@RequestParam int num) {
        return AjaxResult.success(esService.insertES(num));
    }
}
