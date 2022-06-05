package com.queueup.qup.controller.admin;

import com.queueup.qup.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/usersList")
public class AdminUsersController{
    private final UserServiceImpl userService;

    public AdminUsersController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping
    public String openUserPage(Model model){
        model.addAttribute("userList",userService.findAll());
        return "admin/users";
    }
}

