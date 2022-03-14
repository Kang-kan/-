package com.markerhub.controller;


import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index(){
        System.out.println("数据输出"+userService.getById(1L));
         User user = userService.getById(1L);
         return Result.message(user);
    }
    @GetMapping("/indexs/{id}")
    public Result index(@PathVariable("id") Long id){
        return Result.message(userService.getById(id));
    }

    @PostMapping("save")
    public Result save(@Validated @RequestBody User user){
        return Result.message(user);
    }

}
