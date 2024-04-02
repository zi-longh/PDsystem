package com.zilong.fdbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.exception.CustomException;
import com.zilong.fdbackend.mapper.AccountMapper;
import com.zilong.fdbackend.pojo.AccountPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    public AccountPojo login(AccountPojo accountPojo) {
        AccountPojo dbAccountPojo = accountMapper.selectById(accountPojo.getUsername());
        if (dbAccountPojo == null) {
            throw new CustomException("账号不存在！");
        }
        if (!accountPojo.getPassword().equals(dbAccountPojo.getPassword())) {
            throw new CustomException("账号密码错误！");
        }
        if (!accountPojo.getRole().equals(dbAccountPojo.getRole())) {
            throw new CustomException("账号类型错误！");
        }
        return dbAccountPojo;
    }

    public AccountPojo register(AccountPojo accountPojo) {
        AccountPojo dbAccountPojo = accountMapper.selectById(accountPojo.getUsername());
        if (dbAccountPojo != null) {
            throw new CustomException("账号已存在！");
        }
        accountMapper.insert(accountPojo);
        return accountPojo;
    }

    public void addAccount(String username, String password, String accountType) {
        accountMapper.insert(new AccountPojo(username, password, accountType));
    }

    public void deleteAccount(String username) {
        accountMapper.deleteById(username);
    }

    public Result updatePassword(AccountPojo accountPojo) {
        AccountPojo dbAccountPojo = accountMapper.selectById(accountPojo.getUsername());
        if (dbAccountPojo == null) {
            return Result.error("账号不存在！");
        }
        accountPojo.setRole(dbAccountPojo.getRole());
//        if (accountPojo.getPassword().equals(dbAccountPojo.getPassword())) {
//            return Result.error("新密码不能与旧密码相同！");
//        }
        if (accountPojo.getPassword().length() < 1) {
            return Result.error("密码不能为空！");
        }
        accountMapper.update(accountPojo, new QueryWrapper<AccountPojo>().eq("username", accountPojo.getUsername()));
        return Result.success(accountPojo);
    }


}
