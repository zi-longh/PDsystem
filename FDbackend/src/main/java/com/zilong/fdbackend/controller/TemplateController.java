package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.pojo.TemplatePojo;
import com.zilong.fdbackend.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TemplateController {

    @Autowired
    TemplateService templateService;

    /**
     * 获取模板列表
     */
    @RequestMapping(value = "getTemplateList", method = RequestMethod.GET)
    public Result getTemplateList(){
        return Result.success(templateService.getTemplateList());
    }

    /**
     * 学生端获取模板的下拉框
     */
    @RequestMapping(value = "getTemplateListForStudent", method = RequestMethod.GET)
    public Result getTemplateListForStudent(){
        return templateService.getTemplateListForStudent();
    }

    @RequestMapping(value = "deleteTemplate", method = RequestMethod.POST)
    public Result deleteTemplate(@RequestBody TemplatePojo templatePojo){
        return templateService.deleteTemplate(templatePojo);
    }

    @RequestMapping(value = "getTemplateListForTeacher", method = RequestMethod.GET)
    public Result getTemplateListForTeacher(){
        return templateService.getTemplateListForTeacher();
    }




}
