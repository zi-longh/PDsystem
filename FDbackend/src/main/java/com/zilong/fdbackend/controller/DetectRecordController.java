package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.service.DetectRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetectRecordController {

    @Autowired
    DetectRecordService detectRecordService;

    @GetMapping("/getDetectRecordByUsername")
    public Result getDetectRecordByUsername(@RequestParam String username) {
        return detectRecordService.getDetectRecordByUsername(username);
    }



}
