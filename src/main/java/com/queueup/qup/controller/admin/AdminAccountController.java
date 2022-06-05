package com.queueup.qup.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/account")
public class AdminAccountController {
    @GetMapping
    public String openAdminAccountPage(){
        return "admin/acount";
    }
}
