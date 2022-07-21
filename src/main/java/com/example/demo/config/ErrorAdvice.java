package com.example.demo.config;

import com.example.demo.exception.ArticlePostFailException;
import com.example.demo.exception.BlogIdNotFoundException;
import com.example.demo.exception.ImageNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-20
 * Time: 19:42
 */

/**
 *  发生了异常, 我们也必须完成交互, 否则前端和用户根本不知道该怎样后续处理
 */
@ControllerAdvice // 控制器通知类 (增强 Controller, 它的下级必须有对应的增强方面)
@ResponseBody
public class ErrorAdvice {


    // 异常处理器, 处理顶级异常, 如果发生异常, 找不到对应的父类异常处理方法, 就会走这个方法
    @ExceptionHandler(Exception.class)
    public HashMap<String, Object> exception(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", -1);
        hashMap.put("message", "服务处理异常, 请稍后再试! ");
        return hashMap;
    }

    // 图片路径错误
    @ExceptionHandler(ImageNotFoundException.class)
    public HashMap<String, Object> imageNotFoundException(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", -1);
        hashMap.put("message", "图片加载失败! ");
        return hashMap;
    }


    // 文章标题为空或内容为空
    @ExceptionHandler(ArticlePostFailException.class)
    public HashMap<String, Object> articlePostFailException(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", -1);
        hashMap.put("message", "文章标题或内容不能为空! ");
        return hashMap;
    }

    // 博客id未找到
    @ExceptionHandler(BlogIdNotFoundException.class)
    public HashMap<String, Object> blogIdNotFoundException(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", -1);
        hashMap.put("message", "博客或 博客 ID 不存在! ");
        return hashMap;
    }

    // 异常处理器, 处理空指针异常
    @ExceptionHandler(NullPointerException.class)
    public HashMap<String, Object> nullException(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", -1);
        hashMap.put("message", "发生空指针异常! ");
        return hashMap;
    }

}
