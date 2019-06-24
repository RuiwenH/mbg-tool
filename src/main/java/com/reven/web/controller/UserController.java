package com.reven.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reven.model.entity.User;
import com.reven.service.IUserService;
import com.reven.core.ResResult;
import com.reven.core.BaseController;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
* Created by CodeGenerator on 2019/06/24.
*/
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private IUserService userService;

    @PostMapping("/add")
    public ResResult add(User user) {
        userService.save(user);
        return ResResult.success();
    }

    @PostMapping("/delete")
    public ResResult delete(@RequestParam Integer id) {
        userService.deleteById(id);
        return ResResult.success();
    }

    @PostMapping("/update")
    public ResResult update(User user) {
        userService.update(user);
        return ResResult.success();
    }

    @RequestMapping("/detail")
    public ResResult detail(@RequestParam Integer id) {
        User user = userService.findById(id);
        return ResResult.success(user);
    }

    @RequestMapping("/list")
    public ResResult list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return ResResult.success(pageInfo);
    }
}
