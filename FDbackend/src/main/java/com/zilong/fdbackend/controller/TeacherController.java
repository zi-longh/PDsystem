package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @RequestMapping(value = "/getAllTeacher", method = RequestMethod.GET)
    public Result getAllTeacher() {
        return teacherService.getAllTeacher();
    }
}
