package com.example.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-20
 * Time: 21:31
 */

/**
 * 统一返回格式
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {

    // 该方法的返回值为true, 表明所有的返回进行封装
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    // 内容返回前进行包装, 包装后再返回
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("success", 200);

        // 如果传参发现不是一个具体数据对象, 而是一个 HashMap
        // 可能是以下的情况
        //  1.  controller 参数错误
        //  2. 统一异常放处理
        //  3. 多种类似的资源的一次请求 (登陆者的文章数, 访客, 图片...)
        if (body instanceof HashMap) {

            HashMap<String, Object> acceptMap = (HashMap<String, Object>) body;
            hashMap.put("status", (int) acceptMap.get("status"));

            // 异常错误会有具体信息, 或者 例如一些操作失败的具体原因 (例如登录失败, 是密码错误, 还是用户名不存在)
            if (acceptMap.get("message") != null) {
                hashMap.put("data", acceptMap.get("message"));
                return hashMap;
            }
            return hashMap;
        }

        // 返回具体对象信息
        hashMap.put("status", 1);
        hashMap.put("data", body);
        return hashMap;
    }
}
