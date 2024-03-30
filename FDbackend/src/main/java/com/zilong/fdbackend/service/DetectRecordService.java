package com.zilong.fdbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.mapper.DetectRecordMapper;
import com.zilong.fdbackend.pojo.DetectRecordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetectRecordService {

    @Autowired
    DetectRecordMapper detectRecordMapper;

    public Result getDetectRecordByUsername(String username) {
        List<DetectRecordPojo> usernameList = detectRecordMapper.selectList(new QueryWrapper<DetectRecordPojo>().eq("username", username));
        return Result.success(usernameList);
    }



}
