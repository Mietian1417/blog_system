

drop database if exists blog_system;
create database blog_system;

use blog_system;

drop table if exists user_message;
create table user_message
(
    user_id int primary key auto_increment,

    user_name varchar(128) unique,
    user_password varchar(128),

    blog_numbers int default 0,

    image_url varchar(1024) default 'unkown'
);

insert into user_message(user_name, user_password, blog_numbers) values
    ('张三', '123456', 3),
    ('李四', '123123', 1),
    ('admin', 'admin', 0);



drop table if exists blog_message;
create table blog_message(
    blog_id int primary key auto_increment,

    title varchar(1024),
--     下面这个 mediumtext 是一个文本字符串类型, 可以很长, 适用于那些不确定长度的文本
    content mediumtext,
    post_time datetime,
    user_id int,

    visitors int default 0,
--    逻辑删除, 1 表示存在, 0 表示删除
    status int default  1
);

insert into blog_message(title, content, post_time, user_id) values
    ('文章1','文章1的正文............', now(), 1),
    ('文章2','文章2的正文............', now(), 2),
    ('文章3','文章3的正文............', now(), 1),
    ('文章4','文章4的正文............', now(), 1);