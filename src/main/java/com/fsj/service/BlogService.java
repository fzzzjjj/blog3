package com.fsj.service;

import com.fsj.po.Blog;
import com.fsj.po.User;
import com.fsj.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable, User user, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);
    //
    Page<Blog> listBlog(Pageable pageable,Long id);

    Page<Blog> listBlog1(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    Page<Blog> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    //已发布文章显示
    Page<Blog> findPublished(Pageable pageable);
}
