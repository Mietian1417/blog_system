package com.example.demo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-11
 * Time: 17:38
 */

@Transactional
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void saveImageUrl() {
        userMapper.saveImageUrl(1,"unkown");
    }

    @Test
    void getImageUrl() {
        String s1 = userMapper.getImageUrl(1);
        System.out.println(s1);

        String s2 = userMapper.getImageUrl(2);
        System.out.println(s2);

        String s3 = userMapper.getImageUrl(3);
        System.out.println(s3);


    }
}