package com.clrvn.controller;

import com.clrvn.enums.PaperTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    public String index(HttpSession session) {
        //默认，不用登录
        /*User user1 = new User();
        user1.setId(1);
        user1.setUsername("User");
        user1.setPassword("46f94c8de14fb36680850768ff1b7f2a");
        user1.setRole(UserRoleConstant.ROLE_USER);
        
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("Admin");
        user2.setPassword("46f94c8de14fb36680850768ff1b7f2a");
        user2.setRole(UserRoleConstant.ROLE_ADMIN);*/

        //审批类型枚举列表
        List<PaperTypeEnum> paperTypeEnumList = new ArrayList<>();

        paperTypeEnumList.add(PaperTypeEnum.COMPUTER_TECHNOLOGY);
        paperTypeEnumList.add(PaperTypeEnum.SOFTWARE_ENGINEERING);
        paperTypeEnumList.add(PaperTypeEnum.COMMUNICATION);
        paperTypeEnumList.add(PaperTypeEnum.ELECTRONIC);

//        session.setAttribute("USER", user2);
        session.setAttribute("PAPER_TYPE_LIST", paperTypeEnumList);

//        session.setAttribute("USER", user2);
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
