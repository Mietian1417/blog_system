package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-05
 * Time: 15:14
 */

@Mapper
public interface UserMapper {
    int add(User user);

    User selectById(Integer id);

    User selectByName(String userName);

    int increaseBlogNumbers(Integer id);

    int reduceBlogNumber(Integer id);

    int saveImageUrl(Integer userId, String url);

    String getImageUrl(Integer userId);
}
