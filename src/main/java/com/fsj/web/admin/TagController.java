package com.fsj.web.admin;

import com.fsj.po.Tag;
import com.fsj.po.User;
import com.fsj.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;



@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags-user")
    public String tags_user(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model, HttpSession session,Tag tag) {

        User user = (User) session.getAttribute("user");
        model.addAttribute("page",tagService.listTag(pageable,user,tag));

        return "user/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }
    @GetMapping("/tags-user/input")
    public String input_user(Model model) {
        model.addAttribute("tag", new Tag());
        return "user/user-tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    @GetMapping("/tags-user/{id}/input")
    public String editInput_user(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "user/user-tags-input";
    }


    @PostMapping("/tags")
    public String post(@Valid Tag tag,BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }
    //user更新
    @PostMapping("/tags-user/{id}")
    public String editPost_user(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes
                                    ,HttpSession session,Model model,User user,Pageable pageable) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        //想个办法把id set进去.
        System.out.println("set user");
        User user1 = (User) session.getAttribute("user");
        tag.setUser(user1);
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "user/user-tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        System.out.println("返回user/tags");
        model.addAttribute("page",tagService.listTag(pageable,user1,tag));
        return "user/tags";
    }


    //user tag 新增
    @PostMapping("/tags-user-insert")
    public String editPost_user_insert(@Valid Tag tag, BindingResult result, RedirectAttributes attributes
            ,HttpSession session,Model model,User user,Pageable pageable) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        //想个办法把id set进去.
        System.out.println("set user");
        User user1 = (User) session.getAttribute("user");
        tag.setUser(user1);
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "user/user-tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        System.out.println("返回user/tags");
        model.addAttribute("page",tagService.listTag(pageable,user1,tag));
        return "user/tags";
    }


    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags-user/{id}/delete")
    public String delete_user(@PathVariable Long id,RedirectAttributes attributes
    ,HttpSession session,Model model,User user,Pageable pageable,Tag tag) {
        tagService.deleteTag(id);
        User user1 = (User) session.getAttribute("user");
        model.addAttribute("page",tagService.listTag(pageable,user1,tag));
        attributes.addFlashAttribute("message", "删除成功");
        return "user/tags";
    }




}
