package com.queueup.qup.controller.admin;

import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/adminPanel")
public class AdminController{

    @Autowired
    UserRepo userRepo;
    @GetMapping
    public String openAdminPage(Model model){
        model.addAttribute("totalUser ", userRepo.totalUsers());
        return "admin/adminPanel";
    }


}
