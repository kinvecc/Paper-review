package com.clrvn.controller;

import com.clrvn.entity.User;
import com.clrvn.enums.ResultFailureEnum;
import com.clrvn.service.IUserService;
import com.clrvn.utils.AppMD5Util;
import com.clrvn.utils.ResultVOUtil;
import com.clrvn.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public ResultVO login(@RequestBody User loginUser, HttpSession session) {
        //MD5转码
        loginUser.setPassword(AppMD5Util.getMD5(loginUser.getPassword()));
        User user = userService.login(loginUser);
        if (user != null) {
            session.setAttribute("USER", user);
            return ResultVOUtil.success();
        } else {
            return ResultVOUtil.failure(ResultFailureEnum.LOGIN_ERROR);
        }

    }

    @PostMapping("/register")
    public ResultVO register(@RequestBody User registerUser, HttpSession session) {
        //MD5转码
        registerUser.setPassword(AppMD5Util.getMD5(registerUser.getPassword()));
        User user = userService.register(registerUser);
        session.setAttribute("USER", user);
        return ResultVOUtil.success();
    }


}
