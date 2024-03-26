package com.zilong.fdbackend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "account")
public class Account {

    @TableId(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "role")
    private String role;


}
