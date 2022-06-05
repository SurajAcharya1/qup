package com.queueup.qup.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/adminPanel")
public class AdminController{
    @GetMapping
    public String openAdminPage(){
        return "admin/adminPanel";
    }
}
