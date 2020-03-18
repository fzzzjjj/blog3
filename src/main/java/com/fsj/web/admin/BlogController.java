package com.fsj.web.admin;

import com.fsj.po.Blog;
import com.fsj.po.User;
import com.fsj.vo.BlogQuery;
import com.fsj.service.BlogService;
import com.fsj.service.TagService;
import com.fsj.service.TypeService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs-user")
    public String blogs_user(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model,HttpSession session) {

        model.addAttribute("types", typeService.listType());
        User user = (User) session.getAttribute("user");
        model.addAttribute("page", blogService.listBlog(pageable,user, blog));
        return "user/user-blogs";
    }
    //admin的blogs列表
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model,HttpSession session) {

        model.addAttribute("types", typeService.listType());
//        User user = (User) session.getAttribute("user");
//        model.addAttribute("page", blogService.listBlog(pageable,user, blog));
        model.addAttribute("page",blogService.listBlog( pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }


    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }
    @GetMapping("/blogs-user/input")
    public String input_user(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        System.out.println("进入编辑页面");
        return "user/user-blogs-input";
    }


    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }


    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }
    //审批建议
    @GetMapping("/blogs/{id}/opinion")
    public String editInput_opinion(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs-input-opinion";
    }
    @GetMapping("/blogs-user/{id}/input")
    public String editInput_user(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "user/user-blogs-input";
    }



//admin 新增blog
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session,
                       @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,Model model,BlogQuery blog1) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }
    //提交opinion
    @PostMapping("/blogs-opinion")
    public String post_opinion(Blog blog, RedirectAttributes attributes, HttpSession session,
                       @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                               Model model,BlogQuery blog1) {
//        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }



    @PostMapping("/blogs-user")
    public String post_user(Blog blog, RedirectAttributes attributes, HttpSession session
    ,@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,Model model,BlogQuery blog1) {
        blog.setUser((User) session.getAttribute("user"));
//        User user = session.getAttribute("user")
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        System.out.println("redirecr:/user/user-index");
        model.addAttribute("types", typeService.listType());
        User user = (User) session.getAttribute("user");
        model.addAttribute("page", blogService.listBlog(pageable,user, blog1));
        return "user/user-blogs";
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs-user/{id}/delete")
    public String delete_user(@PathVariable Long id,RedirectAttributes attributes
            ,Model model,HttpSession session, @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,BlogQuery blog1) {

        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        model.addAttribute("types", typeService.listType());
        User user1 = (User) session.getAttribute("user");
        model.addAttribute("page", blogService.listBlog(pageable,user1, blog1));
        return "user/user-blogs";
    }

}
