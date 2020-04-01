package com.clrvn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Clrvn
 * @description
 * @className HelloController
 * @date 2019-05-16 21:54
 */
@Controller
public class HelloController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute("USER");
        session.invalidate();
        session.getServletContext().removeAttribute(session.getId());
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/paperList")
    public String paperList() {
        return "paperList";
    }

    @GetMapping("/paperAdd")
    public String paperAdd() {
        return "paperAdd";
    }

    @GetMapping("/userPaperManage")
    public String userPaperManage() {
        return "userPaperManage";
    }

    @GetMapping("/systemPaperManage")
    public String systemPaperManage() {
        return "systemPaperManage";
    }


}
