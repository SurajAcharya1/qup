package com.queueup.qup.controller;

import com.queueup.qup.dto.LoginDto;
import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LogInController {

    @Autowired
    UserRepo userRepo;

    public Integer loggedInUserid;

    @PostMapping("/create")
    public String login(@ModelAttribute LoginDto loginDto, RedirectAttributes redirectAttributes){
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        loggedInUserid = userRepo.getUserId(email);
        try {
            if (userRepo.getUserByEmail(email).equals(email) && userRepo.getUserByPassword(email).equals(password)) {
                if(userRepo.getRole(email)==null){
                    return "redirect:/user/userPanel/";
                }
                else{
                    return "redirect:/admin/adminPanel/";
                }
            }
            else{
                redirectAttributes.addFlashAttribute("loginMessage", "Invalid Username or password");
                return "redirect:/";
            }
        }catch (Exception e){
            System.out.println(e);
            redirectAttributes.addFlashAttribute("loginMessage", "Invalid Username or password");
            return "redirect:/";
        }
    }
}
