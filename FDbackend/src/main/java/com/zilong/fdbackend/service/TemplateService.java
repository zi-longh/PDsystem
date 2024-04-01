package com.zilong.fdbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.mapper.TemplateMapper;
import com.zilong.fdbackend.pojo.TemplatePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    @Autowired
    TemplateMapper templateMapper;

    /**
     * 获取模板列表
     */
    public List<TemplatePojo> getTemplateList() {
        return templateMapper.selectList(null);
    }

    /**
     * 学生端获取模板的下拉框
     */
    public Result getTemplateListForStudent() {
        // template表里status为1的模板
        List<TemplatePojo> templateList = templateMapper.selectList( new QueryWrapper<TemplatePojo>().eq("status", 1));
        for (TemplatePojo templatePojo : templateList) {
            templatePojo.setTemplateName(templatePojo.getTemplateId() + "-" + templatePojo.getTemplateName());
        }
        return Result.success(templateList);
    }



}
