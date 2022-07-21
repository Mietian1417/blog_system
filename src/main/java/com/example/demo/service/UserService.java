package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-05
 * Time: 15:49
 */

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int add(User user){
        return userMapper.add(user);
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public User selectByName(String userName) {
        return userMapper.selectByName(userName);
    }

    public int increaseBlogNumbers(Integer id) {
        return userMapper.increaseBlogNumbers(id);
    }

    public int reduceBlogNumber(Integer id) {
        return userMapper.reduceBlogNumber(id);
    }

    public int saveImageUrl(Integer userId, String url) {
        return userMapper.saveImageUrl(userId, url);
    }

    public String getImageUrl(Integer userId) {
        return userMapper.getImageUrl(userId);
    }
}
