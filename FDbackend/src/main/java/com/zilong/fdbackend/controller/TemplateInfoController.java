package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.pojo.TemplateInfoPojo;
import com.zilong.fdbackend.pojo.TemplatePojo;
import com.zilong.fdbackend.pojo.forStr.TemplateInfoForStr;
import com.zilong.fdbackend.service.TemplateInfoService;
import com.zilong.fdbackend.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TemplateInfoController {
    @Autowired
    TemplateInfoService templateInfoService;

    @Autowired
    TemplateService templateService;

    @GetMapping(value = "/getTemplateInfoById")
    public Result getTemplateInfoById(@RequestParam String template_id){
        TemplateInfoForStr templateInfoForStr = templateInfoService.getTemplateInfoById(template_id);
        return Result.success(templateInfoForStr);
    }

    @GetMapping(value = "/getTemplateInfoById2")
    public Result getTemplateInfoById2(@RequestParam String template_id){
        TemplateInfoPojo templateInfoPojo = templateInfoService.getTemplateInfoById2(template_id);
        return Result.success(templateInfoPojo);
    }

    @RequestMapping(value = "/addTemplateInfo", method = RequestMethod.POST)
    public Result addTemplate(@RequestBody TemplateInfoPojo templateInfoPojo){
        return templateInfoService.addTemplateInfo(templateInfoPojo);
    }

    @RequestMapping(value = "/addTemplate", method = RequestMethod.POST)
    public Result addTemplate(@RequestBody TemplatePojo templatePojo){
        // 根据时间戳生成模板id
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMHHmmss");
        templatePojo.setTemplateId(dateTime.format(formatter));

        // 格式化时间
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter2);

        templatePojo.setCreateTime(formattedDateTime);
        templatePojo.setUpdateTime(formattedDateTime);
        return templateService.addTemplate(templatePojo);
    }

    @RequestMapping(value = "/updateTemplateInfo", method = RequestMethod.POST)
    public Result updateTemplateInfo(@RequestBody TemplateInfoPojo templateInfoPojo){
        return templateInfoService.updateTemplateInfo(templateInfoPojo);
    }



}
