package com.queueup.qup.controller.user;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.dto.LoginDto;
import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/account")
public class UserAccountController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    LogInController logInController;
    public LoginDto loginDto;
    @GetMapping("/{user_name}")
    public String openUserAccountPage(Model model, @PathVariable("user_name") String user_name){
        try{
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name))==null){
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
                model.addAttribute("userDetails",userRepo.getUserDetailsById(logInController.loggedInUserid));
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
