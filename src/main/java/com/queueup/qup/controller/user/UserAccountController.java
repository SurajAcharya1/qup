package com.queueup.qup.controller.user;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.dto.LoginDto;
import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/account")
public class UserAccountController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    LogInController logInController;
    public LoginDto loginDto;
    @GetMapping
    public String openUserAccountPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid)==null){
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                return "users/userAccount";
            }
            else{
                return "error";
            }
        }catch (Exception e){
            return "error";
        }
    }
}
