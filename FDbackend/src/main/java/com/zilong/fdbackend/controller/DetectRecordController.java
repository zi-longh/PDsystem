package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.pojo.DetectRecordPojo;
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
    public Result deleteRecordById(@RequestBody Integer recordId) {
        System.out.println("-----------------deleteRecordById");
        System.out.println("recordId: " + recordId);
        System.out.println("-----------------deleteRecordById");
        return detectRecordService.deleteRecordById(recordId);
    }

    @RequestMapping(value = "/addRecord", method = RequestMethod.POST)
    public Result addRecord(@RequestBody DetectRecordPojo record) {
        detectRecordService.addRecord(record);
        return Result.success(record);
    }


}
