package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.service.DetectRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DetectRecordController {

    @Autowired
    DetectRecordService detectRecordService;

    @GetMapping(value = "/getHistoryByUsername")
    public Result getHistoryByUsername(@RequestParam String username) {
        return detectRecordService.getHistoryByUsername(username);
    }

    @RequestMapping(value = "/deleteRecordById", method = RequestMethod.POST)
    public Result deleteRecordById(@RequestBody String recordId) {
        return detectRecordService.deleteRecordById(recordId);
    }



}
