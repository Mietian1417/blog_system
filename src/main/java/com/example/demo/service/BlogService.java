package com.example.demo.service;

import com.example.demo.mapper.BlogMapper;
import com.example.demo.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-05
 * Time: 15:47
 */

@Service
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;
    public int add(Blog blog) {
        return blogMapper.add(blog);
    }

    public List<Blog> selectAll() {
        return blogMapper.selectAll();
    }

    public Blog selectById(Integer id) {
        return blogMapper.selectById(id);
    }

    public int deleteById(Integer id) {
        return blogMapper.deleteById(id);
    }

    public int updateVisitors(Integer id){
        return blogMapper.updateVisitors(id);
    }

    public Integer getVisitors(Integer userId) {
        return blogMapper.getVisitors(userId);
    }

    public List<Blog> getPersonalBlogs(Integer userId) {
        return blogMapper.getPersonalBlogs(userId);
    }
}
