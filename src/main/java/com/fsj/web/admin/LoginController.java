package com.fsj.web.admin;

import com.alibaba.fastjson.JSONObject;
import com.fsj.po.User;
import com.fsj.result.Result;
import com.fsj.service.BlogService;
import com.fsj.service.TagService;
import com.fsj.service.TypeService;
import com.fsj.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.PrintWriter;

/**
 * Created by limi on 2017/10/15.
 */
@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;
    public static Long i;
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login( @RequestParam String username,
                        @RequestParam String password,
                         @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)  Pageable pageable,
                        HttpSession session,
                        RedirectAttributes attributes,
                         HttpServletRequest request,

                        Model model) {
        User user = userService.checkUser(username, password);


        /**
         * 非空验证，返回index
         * 空返回登录页面
         */
/*        if("admin".equals(user.getUsername())){
            user.setPassword(null);
            session.setAttribute("user",user);

            model.addAttribute("page",blogService.listBlog(pageable));
            model.addAttribute("types", typeService.listTypeTop(6));
            model.addAttribute("tags", tagService.listTagTop(10));
            return "admin/adminindex";
        }*/
        if (user != null) {
            if("admin".equals(user.getUsername())){
                user.setPassword(null);
                session.setAttribute("user",user);

                model.addAttribute("page",blogService.listBlog(pageable));
                model.addAttribute("types", typeService.listTypeTop(6));
                model.addAttribute("tags", tagService.listTagTop(10));
                model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
                return "admin/adminindex";
            }else
                //登录成功
            System.out.println("登录成功");
            user.setPassword(null);
            System.out.println(user.getId());
             i = user.getId();
            session.setAttribute("user",user);
            model.addAttribute("page",blogService.listBlog(pageable));
            model.addAttribute("types", typeService.listTypeTop(6));
            model.addAttribute("tags", tagService.listTagTop(10));

            return "user/user-index";
        } else {
            //登录失败
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "admin/login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @RequestMapping("/do_login")
    @ResponseBody
        public Result doLogin(HttpServletResponse response, @Valid User user) {
        return Result.success("1111") ;
    }


}