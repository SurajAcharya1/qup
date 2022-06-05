package com.queueup.qup.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/userPanel")
public class UserController{
    @GetMapping
    public String openUserPanelPage(){
        return "users/userPanel";
    }
}