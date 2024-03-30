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
@TableName(value = "detect_record")
public class DetectRecordPojo {

    @TableId(value = "record_id")
    private String recordId;

    private String name;

    @TableField(value = "student_id")
    private String studentId;

    private String grade;
    private String major;
    private String department;
    private String instructor;

}
