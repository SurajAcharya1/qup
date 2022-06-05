package com.queueup.qup.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/history")
public class UserHistoryController{
    @GetMapping
    public String openUserHistoryPage(){
        return "users/userHistory";
    }
}
