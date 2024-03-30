package com.zilong.fdbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zilong.fdbackend.pojo.StudentPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<StudentPojo> {
}
