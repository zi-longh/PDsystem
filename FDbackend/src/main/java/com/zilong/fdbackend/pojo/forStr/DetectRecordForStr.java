package com.zilong.fdbackend.pojo.forStr;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zilong.fdbackend.mapper.TemplateMapper;
import com.zilong.fdbackend.pojo.DetectRecordPojo;
import com.zilong.fdbackend.pojo.TemplatePojo;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DetectRecordForStr {


    public DetectRecordForStr(DetectRecordPojo detectRecordPojo) {
        this.recordId = Integer.toString(detectRecordPojo.getRecordId());
        this.username = detectRecordPojo.getUsername();


        TemplateMapper templateMapper = SpringUtil.getBean(TemplateMapper.class);
        TemplatePojo templatePojo = templateMapper.selectOne(new QueryWrapper<TemplatePojo>().eq("template_id", detectRecordPojo.getTemplateId()));
        if (templatePojo == null) {
            this.templateId = detectRecordPojo.getTemplateId();
        } else {
            this.templateId = templatePojo.getTemplateId() + "-" + templatePojo.getTemplateName();
        }


        this.detectTime = detectRecordPojo.getDetectTime();

        if (Objects.equals(detectRecordPojo.getStatus(), "0")) {
            this.status = "检测通过";
        } else if (Objects.equals(detectRecordPojo.getStatus(), "1")) {
            this.status = "检测通过(可修改)";
        } else if (Objects.equals(detectRecordPojo.getStatus(), "2")) {
            this.status = "不通过";
        } else {
            this.status = "未知状态";
        }

        this.paperName = detectRecordPojo.getPaperName();
        this.paperEnglishName = detectRecordPojo.getPaperEnglishName();
        this.resultFileName = detectRecordPojo.getResultFileName();
        this.resultPDF = detectRecordPojo.getResultPDF();
        this.isSendToTeacher = Objects.equals(detectRecordPojo.getIsSendToTeacher(), "1") ? "是" : "否";
        this.teacherUsername = detectRecordPojo.getTeacherUsername();
    }

    private String recordId;

    private String username;

    private String templateId;

    private String detectTime;

    private String status;

    private String paperName;

    private String paperEnglishName;

    private String resultFileName;

    private String resultPDF;

    private String isSendToTeacher;

    private String teacherUsername;
}
