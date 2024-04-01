package com.zilong.fdbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.mapper.DetectRecordMapper;
import com.zilong.fdbackend.pojo.DetectRecordPojo;
import com.zilong.fdbackend.pojo.forStr.DetectRecordForStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetectRecordService {

    @Autowired
    DetectRecordMapper detectRecordMapper;

    public Result getHistoryByUsername(String username) {
        List<DetectRecordPojo> recordList = detectRecordMapper.selectList(new QueryWrapper<DetectRecordPojo>().eq("username", username));
        List<DetectRecordForStr> recordListForStr = new ArrayList<>();
        for (DetectRecordPojo record : recordList) {
            recordListForStr.add(new DetectRecordForStr(record));
        }
        return Result.success(recordListForStr);
    }

    public Result deleteRecordById(int recordId) {
        if (detectRecordMapper.deleteById(recordId) == 1) {
            return Result.success();
        } else {
            return Result.error("删除失败");
        }
    }

    public void addRecord(DetectRecordPojo record) {
        detectRecordMapper.insert(record);
    }


}
