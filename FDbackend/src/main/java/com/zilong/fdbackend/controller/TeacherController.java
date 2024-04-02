package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.pojo.TeacherPojo;
import com.zilong.fdbackend.service.DetectRecordService;
import com.zilong.fdbackend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @Autowired
    DetectRecordService detectRecordService;

    @RequestMapping(value = "/getAllTeacher", method = RequestMethod.GET)
    public Result getAllTeacher() {
        return teacherService.getAllTeacher();
    }

    @RequestMapping(value = "/getTeacherInfoByUserName", method = RequestMethod.POST)
    public Result getTeacherInfoByUserName(@RequestBody TeacherPojo teacherPojo) {
        String username = teacherPojo.getUsername();
        return teacherService.getTeacherInfoByUserName(username);
    }
    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    public Result updateTeacher(@RequestBody TeacherPojo teacherPojo) {
        return teacherService.updateTeacher(teacherPojo);
    }

    @RequestMapping(value = "/getPapersOfStu", method = RequestMethod.POST)
    public Result getPapersOfStu(@RequestBody TeacherPojo teacherPojo) {
        String username = teacherPojo.getUsername();
        return detectRecordService.getPapersOfStu(username);
    }


}
