package com.zilong.fdbackend.service;

import com.zilong.fdbackend.exception.CustomException;
import com.zilong.fdbackend.mapper.AccountMapper;
import com.zilong.fdbackend.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    public Account login(Account account) {
        Account dbAccount = accountMapper.selectById(account.getUsername());
        if (dbAccount == null) {
            throw new CustomException("账号不存在！");
        }
        if (!account.getPassword().equals(dbAccount.getPassword())) {
            throw new CustomException("账号密码错误！");
        }
        return dbAccount;
    }

    public void addAccount(String username, String password, String accountType) {
        accountMapper.insert(new Account(username, password, accountType));
    }

    public void deleteAccount(String username) {
        accountMapper.deleteById(username);
    }


}
