package com.queueup.qup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("logout")
public class LogoutController {

    @GetMapping()
    public String logout(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("logoutMessage", "Logged Out Successfully");
        return "redirect:/";
    }
}


