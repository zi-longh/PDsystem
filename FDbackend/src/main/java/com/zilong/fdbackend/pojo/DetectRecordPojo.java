package com.zilong.fdbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "detect_record")
public class DetectRecordPojo {

    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    @TableField(value = "username")
    private String username;

    @TableField(value = "template_id")
    private String templateId;

    @TableField(value = "detect_time")
    private String detectTime;

    @TableField(value = "status")
    private String status;

    @TableField(value = "paperName")
    private String paperName;

    @TableField(value = "paperEnglishName")
    private String paperEnglishName;

    @TableField(value = "resultFileName")
    private String resultFileName;

    @TableField(value = "resultPDF")
    private String resultPDF;

    @TableField(value = "isSendToTeacher")
    private String isSendToTeacher;

}
