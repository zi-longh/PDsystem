package com.zilong.fdbackend.service;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.mapper.TemplateInfoMapper;
import com.zilong.fdbackend.pojo.TemplateInfoPojo;
import com.zilong.fdbackend.pojo.forStr.TemplateInfoForStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateInfoService {

    @Autowired
    TemplateInfoMapper templateInfoMapper;

    public TemplateInfoForStr getTemplateInfoById(String template_id) {
//        System.out.println("template_id: " + template_id);
        return new TemplateInfoForStr(templateInfoMapper.selectById(template_id));
    }

    public TemplateInfoPojo getTemplateInfoById2(String template_id) {
        return templateInfoMapper.selectById(template_id);
    }

    public Result addTemplateInfo(TemplateInfoPojo templateInfoPojo) {
        int ret = templateInfoMapper.insert(templateInfoPojo);
        if (ret == 1) {
            return Result.success();
        } else {
            return Result.error("添加失败");
        }
    }

    public Result updateTemplateInfo(TemplateInfoPojo templateInfoPojo) {
        int ret = templateInfoMapper.updateById(templateInfoPojo);
        if (ret == 1) {
            return Result.success();
        } else {
            return Result.error("更新失败");
        }
    }


}
