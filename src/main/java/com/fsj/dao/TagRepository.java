package com.fsj.dao;

import com.fsj.po.Blog;
import com.fsj.po.Tag;
import com.fsj.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

    Page<Tag> findByUser(Pageable pageable,User user);

}
