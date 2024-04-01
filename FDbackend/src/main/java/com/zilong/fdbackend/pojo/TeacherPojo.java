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
@TableName(value = "teacher")
public class TeacherPojo {
    @TableId(value = "username")
    private String username;
    private String name;
    @TableField(value = "teacher_id")
    private String teacherId;
    private String department;
    private String description;
}
