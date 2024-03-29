package com.zilong.fdbackend.controller;

import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.exception.CustomException;
import com.zilong.fdbackend.pojo.AccountPojo;
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
    public Result login(@RequestBody AccountPojo accountPojo){
        AccountPojo dbAccountPojo;
        try{
            dbAccountPojo = accountService.login(accountPojo);
            return Result.success(dbAccountPojo);
        } catch (CustomException e) {
            return Result.error(e.getMsg());
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestBody AccountPojo accountPojo){
        try{
            accountService.register(accountPojo);
            return Result.success();
        } catch (CustomException e) {
            return Result.error(e.getMsg());
        }
    }




}
