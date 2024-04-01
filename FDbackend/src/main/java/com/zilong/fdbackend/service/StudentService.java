package com.zilong.fdbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.mapper.StudentMapper;
import com.zilong.fdbackend.pojo.StudentPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    public Result getStudentInfoByUsername(String username) {
        StudentPojo studentPojo = studentMapper.selectOne(new QueryWrapper<StudentPojo>().eq("username", username));
        if (studentPojo == null) {
            return Result.error("用户不存在");
        } else {
            return Result.success(studentPojo);
        }
    }

    public Result updateStudent(StudentPojo studentPojo) {
        int res = studentMapper.update(studentPojo, new QueryWrapper<StudentPojo>().eq("username", studentPojo.getUsername()));
        if (res == 0) {
            return Result.error("更新失败");
        } else {
            return Result.success(studentPojo);
        }
    }

    
}
