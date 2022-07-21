package com.example.demo.model;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-05
 * Time: 15:09
 */

@Data
public class User {
    private int userId;
    private String userName;
    private String password;
    private int blogNumbers;
}
