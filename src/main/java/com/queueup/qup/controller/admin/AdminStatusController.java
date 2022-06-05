package com.queueup.qup.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/status")
public class AdminStatusController {
    @GetMapping
    public String openAdminStatusPage(){
        return "admin/status";
    }
}

