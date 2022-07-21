package com.example.demo.mapper;

import com.example.demo.model.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-05
 * Time: 15:14
 */

@Mapper
public interface BlogMapper {
    List<Blog> selectAll();

    int add(Blog blog);

    Blog selectById(Integer id);

    int deleteById(Integer id);

    int updateVisitors(Integer id);

    Integer getVisitors(Integer userId);

    List<Blog> getPersonalBlogs(Integer userId);

}
