package com.fsj.web;

import com.fsj.po.Blog;
import com.fsj.service.BlogService;
import com.fsj.service.TagService;
import com.fsj.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class BlogShowController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    //文章详情页
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model,
                       @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable)  {
        Long i = id;
        if(i == 1){
          model.addAttribute("blog",blogService.getAndConvert(id));
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
            return "aboutme" ;
        }
        model.addAttribute("blog",blogService.getAndConvert(id));
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "show";
    }


    @GetMapping("/blog-admin/{id}")
    public String blog_admin(@PathVariable Long id, Model model,
                       @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable)  {
        Long i = id;
        if(i == 1){
            model.addAttribute("blog",blogService.getAndConvert(id));
            model.addAttribute("page",blogService.listBlog(pageable));
            model.addAttribute("types", typeService.listTypeTop(6));
            model.addAttribute("tags", tagService.listTagTop(10));
            model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
            return "aboutme" ;
        }
        model.addAttribute("blog",blogService.getAndConvert(id));
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "admin/show-admin";
    }
}
