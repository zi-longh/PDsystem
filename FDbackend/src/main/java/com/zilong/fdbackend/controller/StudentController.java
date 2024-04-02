package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.pojo.StudentPojo;
import com.zilong.fdbackend.service.StudentService;
import com.zilong.fdbackend.service.TeacherService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @RequestMapping(value = "/getStudentInfoByUsername", method = RequestMethod.POST)
    public Result getStudentInfoByUsername(@RequestBody StudentPojo stu) {
        String username = stu.getUsername();
        return studentService.getStudentInfoByUsername(username);
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public Result updateStudent(@RequestBody StudentPojo stu) {
        String teacherId = stu.getInstructor().split("（")[1];
        teacherId = teacherId.substring(0, teacherId.length() - 1);
        // 根据教师id查询教师账号名
        String teacherUsername = teacherService.getTeacherUsernameById(teacherId);
        stu.setInstructor(teacherUsername);
        // 更新学生信息
        return studentService.updateStudent(stu);
    }

    @RequestMapping(value = "/getAllStudentsInfo",method = RequestMethod.GET)
    public Result getAllStudentsInfo(){
        return studentService.getAllStudentsInfo();
    }


}
