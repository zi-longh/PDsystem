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

    private String name;

    @TableField(value = "student_id")
    private String studentId;

    private String major;

    private String grade;

    private String department;

    private String instructor;

}
