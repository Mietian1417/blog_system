package com.example.demo.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-05
 * Time: 15:08
 */

@Data
public class Blog {
    private int blogId;
    private String title;
    private String content;
    private Timestamp postTime;
    private int userId;
    private int visitors;
    private  int status; // 逻辑删除 ,1 表示存在 0 表示删除
}
