package com.queueup.qup.controller.user;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/history")
public class UserHistoryController{

    @Autowired
    UserRepo userRepo;
    @Autowired
    LogInController logInController;
    @GetMapping
    public String openUserHistoryPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid)==null){
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                return "users/userHistory";
            }
            else{
                return "error";
            }
        }catch (Exception e){
            return "error";
        }
    }
}

