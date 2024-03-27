package com.zilong.fdbackend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "template")
public class Template {

    @TableId(value = "template_id")
    private String templateId;

    @TableField(value = "template_name")
    private String templateName;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "description")
    private String description;


}
