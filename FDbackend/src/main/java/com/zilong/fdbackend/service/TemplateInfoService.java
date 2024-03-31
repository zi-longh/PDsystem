package com.zilong.fdbackend.service;

import com.zilong.fdbackend.mapper.TemplateInfoMapper;
import com.zilong.fdbackend.pojo.forStr.TemplateInfoForStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateInfoService {

    @Autowired
    TemplateInfoMapper templateInfoMapper;

    public TemplateInfoForStr getTemplateInfoById(String template_id) {
        System.out.println("template_id: " + template_id);
        return new TemplateInfoForStr(templateInfoMapper.selectById(template_id));
    }


}
