package com.queueup.qup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("logout/")
public class LogoutController {

    @Autowired
    LogInController logInController;

    @GetMapping("{user_name}")
    public String logout(@PathVariable("user_name") String user_name, RedirectAttributes redirectAttributes){
        logInController.loggedInUserDetail.remove(user_name);
        redirectAttributes.addFlashAttribute("logoutMessage", "Logged out successfully");
        return "redirect:/";
    }
}


