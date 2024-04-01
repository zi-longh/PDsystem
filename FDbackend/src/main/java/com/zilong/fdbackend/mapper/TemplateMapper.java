package com.zilong.fdbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zilong.fdbackend.pojo.TemplatePojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface TemplateMapper extends BaseMapper<TemplatePojo> {
}
