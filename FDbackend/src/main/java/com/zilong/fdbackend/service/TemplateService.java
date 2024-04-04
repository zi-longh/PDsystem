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
        List<TemplatePojo> templateList = templateMapper.selectList(new QueryWrapper<TemplatePojo>().eq("status", 1));
        for (TemplatePojo templatePojo : templateList) {
            templatePojo.setTemplateName(templatePojo.getTemplateId() + "-" + templatePojo.getTemplateName());
        }
        return Result.success(templateList);
    }

    public Result getTemplateListForTeacher() {
        // template表里所有模板
        List<TemplatePojo> templateList = templateMapper.selectList(null);
        for (TemplatePojo templatePojo : templateList) {
            String status;
            if (templatePojo.getStatus() == 1) {
                status = "已上线";
            } else {
                status = "未上线";
            }
            templatePojo.setTemplateName(templatePojo.getTemplateId() + "-" + templatePojo.getTemplateName() + "-" + status);
        }
        return Result.success(templateList);
    }

    public Result deleteTemplate(TemplatePojo templatePojo) {
        int i = templateMapper.deleteById(templatePojo.getTemplateId());
        if (i == 1) {
            return Result.success();
        } else {
            return Result.error("删除失败");
        }
    }


}
