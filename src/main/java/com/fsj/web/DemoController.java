package com.fsj.web;

import com.fsj.po.User;
import com.fsj.result.Result;
import com.fsj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {
    @Autowired
    private UserService userService;

/*
    @RequestMapping("find")
    @ResponseBody
    public Result<User> demo(@RequestParam String username){
        return Result.success(userService.findByUsername(username)) ;
    }*/

}
