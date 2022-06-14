package com.queueup.qup.controller.admin;

import com.queueup.qup.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/usersList")
public class AdminUsersController{
    private final UserServiceImpl userService;

    public AdminUsersController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping()
    public String openUserPage(Model model){
        model.addAttribute("userList",userService.findAll());
        return "admin/users";
    }
    @GetMapping("delete/{id}")
    public String deleteUserDetails(@PathVariable("id") Integer id){
        userService.deleteById(id);
        return"redirect:/admin/usersList";
    }
}

