package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-24
 * Time: 18:14
 */
@Configuration
public class SpringMvcWebConfigSupport implements WebMvcConfigurer {

    // 图片资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/userImage/**"). // 访问网站的路径 http://localhost:8080/userImage/***
                //映射后的路径 D://编程语言/Java/spring项目总汇/blog-new-springboot/src/main/resources/static/userImage/
                // 开发环境
//                addResourceLocations("file:" + "D://编程语言/Java/spring项目总汇/blog-new-springboot/src/main/resources/static/userImage/");

                // 生产环境
                        addResourceLocations("file:" + "/home/czh/web/springboot/blog_system/userImage/");

    }

    // 页面拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIInterceptor())
                .addPathPatterns("/**")                          // 拦截所有请求
                .excludePathPatterns("/**/*.css")                // 排除请求
                .excludePathPatterns("/**/*.js")                 // 排除请求
                .excludePathPatterns("/**/*.jpg")                // 排除请求
                .excludePathPatterns("/**/*.png")                // 排除请求
                .excludePathPatterns("/**/blog_login.html")      // 排除请求
                .excludePathPatterns("/**/blog_register.html")   // 排除请求
                .excludePathPatterns("/blog/login")              // 排除请求
                .excludePathPatterns("/blog/register");          // 排除请求
    }
}
