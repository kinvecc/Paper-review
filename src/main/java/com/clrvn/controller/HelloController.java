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

}
