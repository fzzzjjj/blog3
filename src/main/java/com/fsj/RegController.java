package com.fsj;


import com.fsj.po.User;
import com.fsj.service.UserService;
import com.fsj.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class RegController {

    @Autowired
    private UserService userService;

    //添加
    @PostMapping("/registers")
    public String post(@Valid User user, BindingResult result, RedirectAttributes attributes) throws ParseException {
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setType(0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        Date date = null; //初始化date
        date = df.parse(df.format(new Date()));//String 转Date
        user.setUpdateTime(date);

/*        User user1 = userService.saveUser(user);
        if (user1 != null){
            result.rejectValue("username","nameError","不能添加重复的类");
        }
        if (result.hasErrors()){
            return "index";
        }*/
        if (result.hasErrors()) {
            return "registers-input";
        }

        User u = userService.saveUser(user);
        if (u == null){
            //新增失败
            attributes.addFlashAttribute("message","注册失败");
        }else {
            //新增成功
            System.out.println("123");

            attributes.addFlashAttribute("message","注册成功");
        }
        return "admin/login";
    }
    @RequestMapping("/reg")
    public String reg(Model model){
        model.addAttribute("user",new User());

        return "registers-input";
    }



}
