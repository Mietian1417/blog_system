package com.example.demo.controller;

import com.example.demo.exception.ArticlePostFailException;
import com.example.demo.exception.BlogIdNotFoundException;
import com.example.demo.exception.ImageNotFoundException;
import com.example.demo.model.Blog;
import com.example.demo.model.User;
import com.example.demo.service.BlogService;
import com.example.demo.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 陈子豪
 * Date: 2022-04-05
 * Time: 14:11
 */

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Value("${imagePath}")
    private String DIR;

    @Value("${network.address}")
    private String netAddress;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Object login(String userName, String password, HttpServletRequest request) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (userName == null || "".equals(userName) || password == null || "".equals(password)) {
            hashMap.put("message", "用户名或密码为空! ");
            hashMap.put("status", -1);
            return hashMap;
        }

        User user = userService.selectByName(userName);
        if (user == null || !user.getPassword().equals(password)) {
            hashMap.put("message", "用户名或密码错误! ");
            hashMap.put("status", -1);
            return hashMap;
        }

        // true, session 不存在时会创建
        // 添加 session
        HttpSession session = request.getSession(true);
        if (session != null) {
            session.setAttribute("user", user);
        }

        // 正常登录, 返回什么都无所谓
        return 1;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Blog> blogList() {
        List<Blog> blogs = blogService.selectAll();
        return blogs;
    }

    @RequestMapping("/lander")
    @ResponseBody
    public HashMap<String, Object> getListName(@SessionAttribute("user") User user) {
        HashMap<String, Object> hashMap = new HashMap<>();

        User flashUser = userService.selectById(user.getUserId());
        int blogNumbers = flashUser.getBlogNumbers();

        Integer visitors = blogService.getVisitors(user.getUserId());
        if (visitors == null) {
            visitors = 0;
        }

        String url = userService.getImageUrl(user.getUserId());
        HashMap<String, Object> message = new HashMap<>();
        message.put("url", url);
        message.put("userName", user.getUserName());
        message.put("blogNumbers", blogNumbers);
        message.put("visitors", visitors);

        hashMap.put("status", 1);
        hashMap.put("message", message);
        return hashMap;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Blog showDetail(Integer blogId) throws BlogIdNotFoundException {
        if (blogId == null || blogId <= 0) {
            throw new BlogIdNotFoundException();
        }

        Blog blog = blogService.selectById(blogId);
        if (blog == null) {
            throw new BlogIdNotFoundException();
        }
        blogService.updateVisitors(blogId);
        return blog;
    }

    @RequestMapping("/author")
    @ResponseBody
    public Object showAuthor(Integer blogId, @SessionAttribute("user") User user) throws BlogIdNotFoundException {
        HashMap<String, Object> hashMap = new HashMap<>();
        // 登录者信息  @SessionAttribute("user") 获取 user 对象

        if (blogId == null) {
            throw new BlogIdNotFoundException();
        }

        // 表示是从列表页请求的作者身份信息
        if (blogId == -1) {
            User flashUser = userService.selectById(user.getUserId());
            int blogNumbers = flashUser.getBlogNumbers();
            int visitors;
            if (blogService.getVisitors(flashUser.getUserId()) == null) {
                visitors = 0;
            } else {
                visitors = blogService.getVisitors(flashUser.getUserId());

            }

            String url = userService.getImageUrl(flashUser.getUserId());

            HashMap<String, Object> message = new HashMap<>();
            message.put("authorName", flashUser.getUserName());
            message.put("url", url);
            message.put("blogNumbers", blogNumbers);
            message.put("visitors", visitors);

            hashMap.put("status", 1);
            hashMap.put("message", message);
            return hashMap;
        }


        Blog blog = blogService.selectById(blogId);
        if (blog == null) {
            throw new BlogIdNotFoundException();
        }

        // blogId 不为空, 说明是从详情页过来的请求
        int isAuthor = 0; // 不是作者

        if (blog.getUserId() == user.getUserId()) {
            isAuthor = 1;  // 是作者
        }

        User author = userService.selectById(blog.getUserId());
        int blogNumbers = author.getBlogNumbers();

        int visitors = blogService.getVisitors(author.getUserId());

        String url = userService.getImageUrl(author.getUserId());

        HashMap<String, Object> message = new HashMap<>();
        message.put("authorName", author.getUserName());
        message.put("url", url);
        message.put("blogNumbers", blogNumbers);
        message.put("visitors", visitors);
        message.put("isAuthor", isAuthor);

        hashMap.put("status", 1);
        hashMap.put("message", message);
        return hashMap;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("user");
        return "redirect:/blog_login.html";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer deleteBlogById(Integer blogId) throws BlogIdNotFoundException {

        if (blogId == null || blogId <= 0) {
            throw new BlogIdNotFoundException();
        }

        Blog blog = blogService.selectById(blogId);
        if (blog == null) {
            throw new BlogIdNotFoundException();
        }
        int row = blogService.deleteById(blogId);
        if (row != 1) {
            return -1;
        }
        userService.reduceBlogNumber(blog.getUserId());

        // 删除成功, 后续前端以 data 的值来判断后端是否删除成功
        return 1;
    }

    @RequestMapping("/write")
    @ResponseBody
    public Integer write(String title, String content, @SessionAttribute("user") User user) throws ArticlePostFailException {

        if (title == null || "".equals(title) || content == null || "".equals(content)) {
            throw new ArticlePostFailException();
        }

        userService.increaseBlogNumbers(user.getUserId());

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setPostTime(new Timestamp(System.currentTimeMillis()));
        blog.setUserId(user.getUserId());
        int row = blogService.add(blog);
        if (row != 1) {
            return -1;
        }
        return 1;
    }

    @RequestMapping("/register")
    @ResponseBody
    public HashMap<String, Object> register(String username, String password) {
        HashMap<String, Object> hashMap = new HashMap<>();
        int status = 1;
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            hashMap.put("status", -1);  // 用户名或密码为空
            return hashMap;
        }
        User user = userService.selectByName(username);
        if (user != null) {
            hashMap.put("status", -2); // 用户名存在
        } else {
            User addUSer = new User();
            addUSer.setUserName(username);
            addUSer.setPassword(password);
            userService.add(addUSer);
            hashMap.put("status", status);
        }
        return hashMap;
    }

    @RequestMapping("/personal")
    @ResponseBody
    public List<Blog> personalBlogs(@SessionAttribute("user") User user, Integer blogId) throws BlogIdNotFoundException {
        if (blogId == null || blogId < -1){
            throw new BlogIdNotFoundException();
        }
        List<Blog> blogs = null;
        if (blogId == -1) {
            blogs = blogService.getPersonalBlogs(user.getUserId());
        } else {
            Blog blogAuthor = blogService.selectById(blogId);
            blogs = blogService.getPersonalBlogs(blogAuthor.getUserId());
        }

        return blogs;
    }

    @RequestMapping("/image")
    @ResponseBody
    public String saveAndReadImage(@SessionAttribute("user") User user,
                                   @RequestParam("image") MultipartFile imageFile) throws IOException, ImageNotFoundException {

        String imageName = UUID.randomUUID() + imageFile.getOriginalFilename().
                substring(imageFile.getOriginalFilename().indexOf("."));
        String realUrl = DIR + imageName;

//        imageFile.transferTo(new File(realUrl));
        // 上面用下面这个方法即可解决报错问题, 原因尚不清楚...
        FileUtils.copyInputStreamToFile(imageFile.getInputStream(), new File(realUrl));


        // 如果不是首次传图图片, 就删除原来的图片
        String preUrl = userService.getImageUrl(user.getUserId());
        if (!preUrl.equals("unkown")){
            String realName = preUrl.substring(preUrl.lastIndexOf("/") + 1);
            File file = new File(DIR + realName);
            file.delete();
        }

        // 网络路径
        String finalMappingUrl = netAddress + "/userImage/" + imageName;
        // 更新图片路径
        userService.saveImageUrl(user.getUserId(), finalMappingUrl);

        String imageUrl = userService.getImageUrl(user.getUserId());
        if (imageUrl == null){
            throw new ImageNotFoundException();
        }

        return imageUrl;

    }
}
