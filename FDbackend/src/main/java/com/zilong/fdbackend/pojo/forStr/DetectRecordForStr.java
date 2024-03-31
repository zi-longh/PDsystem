package com.zilong.fdbackend.pojo.forStr;

import com.zilong.fdbackend.pojo.DetectRecordPojo;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DetectRecordForStr {

    public DetectRecordForStr(DetectRecordPojo detectRecordPojo) {
        this.recordId = detectRecordPojo.getRecordId();
        this.username = detectRecordPojo.getUsername();
        this.templateId = detectRecordPojo.getTemplateId();
        this.detectTime = detectRecordPojo.getDetectTime();

        if(Objects.equals(detectRecordPojo.getStatus(), "0")) {
            this.status = "检测通过";
        } else if(Objects.equals(detectRecordPojo.getStatus(), "1")) {
            this.status = "检测通过(可修改)";
        } else if(Objects.equals(detectRecordPojo.getStatus(), "2")) {
            this.status = "不通过";
        } else  {
            this.status = "未知状态";
        }

        this.paperName = detectRecordPojo.getPaperName();
        this.paperEnglishName = detectRecordPojo.getPaperEnglishName();
        this.resultFileName = detectRecordPojo.getResultFileName();
        this.resultPDF = detectRecordPojo.getResultPDF();
        this.isSendToTeacher = Objects.equals(detectRecordPojo.getIsSendToTeacher(), "1") ? "是" : "否";
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
}
