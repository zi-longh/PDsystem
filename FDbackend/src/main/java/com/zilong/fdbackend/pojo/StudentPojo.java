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
@TableName(value = "student")
public class StudentPojo {

    @TableId(value = "username")
    private String username;

    @TableField(value = "name")
    private String name;

    @TableField(value = "student_id")
    private String studentId;

    @TableField(value = "grade")
    private String grade;

    @TableField(value = "major")
    private String major;

    @TableField(value = "department")
    private String department;

    @TableField(value = "instructor")
    private String instructor;

}
