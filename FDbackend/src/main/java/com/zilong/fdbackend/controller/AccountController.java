package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.pojo.Account;
import com.zilong.fdbackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestBody Account account){
        Account dbAccount = accountService.login(account);
        return Result.success(dbAccount);
    }




}
