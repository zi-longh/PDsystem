package com.zilong.fdbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.mapper.TeacherMapper;
import com.zilong.fdbackend.pojo.TeacherPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    public Result getAllTeacher() {
        return Result.success(teacherMapper.selectList(null));
    }

    public String getTeacherUsernameById(String teacherId) {
        return teacherMapper.selectOne(new QueryWrapper<TeacherPojo>().eq("teacher_id", teacherId)).getUsername();
    }

    public Result getTeacherInfoByUserName(String username) {
        TeacherPojo teacherPojo = teacherMapper.selectOne(new QueryWrapper<TeacherPojo>().eq("username", username));
        if (teacherPojo == null) {
            return Result.error("No such teacher");
        } else {
            return Result.success(teacherPojo);
        }
    }

    public Result updateTeacher(TeacherPojo teacherPojo) {
        int res = teacherMapper.update(teacherPojo, new QueryWrapper<TeacherPojo>().eq("username", teacherPojo.getUsername()));
        if (res == 0) {
            return Result.error("Update failed");
        } else {
            return Result.success(teacherPojo);
        }
    }
}
