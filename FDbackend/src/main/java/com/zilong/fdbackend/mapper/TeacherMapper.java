package com.zilong.fdbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zilong.fdbackend.pojo.TeacherPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper extends BaseMapper<TeacherPojo> {
}
