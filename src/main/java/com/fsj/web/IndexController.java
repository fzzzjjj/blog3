package com.fsj.web;

import com.fsj.po.User;
import com.fsj.result.CodeMsg;
import com.fsj.result.Result;
import com.fsj.service.BlogService;
import com.fsj.service.TagService;
import com.fsj.service.TypeService;
import com.fsj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @Autowired
    BlogService blogService;
    @Autowired
    TagService tagService;
    @Autowired
    TypeService typeService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index1(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
//        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("page",blogService.findPublished(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }



    @RequestMapping("/type")
    public String show(){
        return "admin/types-show";
    }

    @RequestMapping("/index")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
//        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("page",blogService.findPublished(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(1));
        return "index";
    }




    @RequestMapping("/404")
    public String error1(){
        return "/error/404";
    }

    @RequestMapping("/errors")
    public String error3(){
        return "/error/error";
    }


    @RequestMapping("/500")
    public String error2(){
        return "/error/500";
    }

    @RequestMapping("/types-inputs")
    public String inputtypes(){return "/admin/types-input";}



    @RequestMapping("/asd")
    public String asd(){
        return "_fragments";
    }

    @RequestMapping("/admin/adminindex")
    public String adminindex( Pageable pageable,
                              Model model){
/*
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());*/

        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));


        return "admin/adminindex";
    }
    @RequestMapping("/admin/userindex")
    public String userindex( @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              Model model){




/*        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());*/

        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
//        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
        return "user/user-index";
    }


/*
        @RequestMapping("/search")
        public String search(){
            return "search";
        }
*/

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        model.addAttribute("page1",blogService.listBlog1(pageable));
        return "search";
    }

    @GetMapping("/find")
    @ResponseBody
    public Result<String> findByUsername(@RequestParam String username){

//        return userService.findByUsername(username).getNickname();
        return Result.success(userService.findByUsername(username).getNickname());
//        return Result.error(CodeMsg.SERVER_ERROR);

    }



}
