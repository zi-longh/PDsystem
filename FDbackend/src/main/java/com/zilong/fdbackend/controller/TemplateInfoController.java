package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.pojo.TemplateInfoForStr;
import com.zilong.fdbackend.service.TemplateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TemplateInfoController {
    @Autowired
    TemplateInfoService templateInfoService;

    @GetMapping(value = "/getTemplateInfoById")
    public Result getTemplateInfoById(@RequestParam String template_id){
        TemplateInfoForStr templateInfoForStr = templateInfoService.getTemplateInfoById(template_id);
        return Result.success(templateInfoForStr);
    }
}
