package com.zilong.fdbackend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zilong.fdbackend.pojo.AccountPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<AccountPojo>{
}

