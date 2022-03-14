package com.markerhub.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.User;
import com.markerhub.service.BlogService;
import com.markerhub.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-03-02
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list (@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pageDate = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));
         return Result.message(pageDate);
    }

    /**根据id查询自己的数据*/
    @RequiresAuthentication
    @PostMapping("/homeBlogs")
    public Result homeBlogs (@RequestParam(defaultValue = "1") Integer currentPage){
        /*获取用户信息*/
        AccountProfile accountProfile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        Page page = new Page(currentPage,5);
        IPage pageDate = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created").eq("user_id",accountProfile.getId()));
        return Result.message(pageDate);
    }


    @GetMapping("/blog/{id}")
    public Result detail (@PathVariable(name = "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客已被删除");

     return Result.message(blog);
    }


    /**修改用户信息*/
    @PostMapping("/homeOne")
    public Result homeBlogs (User user){
        blogService.updateHomeOne(user);
         return Result.message(null);
    }
    /**根据id删除*/
    @GetMapping("/deleteBlog/{id}")
    public Result deleteBlog (@PathVariable(name = "id") Long id){
         blogService.update().eq("id", id).remove();
         return Result.message(null);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit (@Validated @RequestBody Blog blog){
        /*1.判断id是否存在,存在则修改,反之则添加*/
          Blog temp = null;
         if(blog.getId()!=null){
            /*获取用户信息*/
            AccountProfile accountProfile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
            /*根据请求的客户ID获取内容*/
             temp = blogService.getById(blog.getId());
            /*如果两个ID不对等,则没有权限操作*/
            Assert.isTrue(accountProfile.getId().longValue() == temp.getUserId().longValue(),"没有权限编辑");
        }else{
            /*获取用户信息*/
            AccountProfile accountProfile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
            temp = new Blog();
            temp.setUserId(accountProfile.getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);
        return Result.message(null);
    }
}
