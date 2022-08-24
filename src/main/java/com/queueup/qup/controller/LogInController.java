package com.queueup.qup.controller;

import com.queueup.qup.dto.LoginDto;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@RequestMapping("/login")
public class LogInController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenRepo tokenRepo;

    public Integer loggedInUserid;
    public String email, password, userName;
    public HashMap<String,String> loggedInUserDetail = new HashMap<String,String>();
    @PostMapping("/create")
    public String login(@ModelAttribute LoginDto loginDto, Model model, RedirectAttributes redirectAttributes){
        email = loginDto.getEmail();
        password = loginDto.getPassword();
        loggedInUserid=userRepo.getUserId(email);
        userName=userRepo.getUserName(email);
//        loggedInUserDetail.put(userRepo.getUserName(email),email);
        try {
            if (userRepo.getUserByEmail(email).equals(email) && userRepo.getUserByPassword(email).equals(password)) {
                if(userRepo.getRole(email)==null){
                    model.addAttribute("userName",userName);
                    loggedInUserDetail.put(userName,userName);
                    return "redirect:/user/userPanel/"+userName;
                }
                else{
                    model.addAttribute("userName",userName);
                    loggedInUserDetail.put(userName,userName);
                    return "redirect:/admin/adminPanel/"+userName;
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
