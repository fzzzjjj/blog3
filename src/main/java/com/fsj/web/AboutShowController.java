package com.fsj.web;

import com.fsj.service.BlogService;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutShowController {

    @Autowired
    private BlogService blogService;
   @RequestMapping("/about")
    public String about(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
       return "aboutme";
    }
/*
    @GetMapping("/blog/1")
    public String blog(@PathVariable Long id, Model model,
                       @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable)  {
        model.addAttribute("blog",blogService.getAndConvert(id));

        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));

        return "show";
    }*/
}
