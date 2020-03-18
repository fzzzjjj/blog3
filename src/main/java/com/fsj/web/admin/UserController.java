package com.fsj.web.admin;

import com.fsj.dao.UserRepository;
import com.fsj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {


    @Autowired
    private UserService userService;

    //显示user
    @GetMapping("/user")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page",userService.findAll(pageable));
        return "admin/user";
    }

    //用户删除
    @GetMapping("/user/{id}/delete")
    public String delete_user(@PathVariable Long id, RedirectAttributes attributes
            , HttpSession session, Model model, @PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable) {
        userService.deleteUser(id);
        model.addAttribute("page",userService.findAll(pageable));
        attributes.addFlashAttribute("message", "删除成功");
        return "admin/user";
    }

}
