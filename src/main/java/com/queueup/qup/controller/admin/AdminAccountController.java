package com.queueup.qup.controller.admin;


import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/account")
public class AdminAccountController {
    @Autowired
    LogInController logInController;
    @Autowired
    UserRepo userRepo;


    @GetMapping
    public String openAdminAccountPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid).equals("ADMIN")) {
                model.addAttribute("userName", userRepo.findNameById(logInController.loggedInUserid));
                model.addAttribute("adminDetails", userRepo.getUserDetailsById(logInController.loggedInUserid));
                return "admin/acount";
            }else {
                return "error";
            }
        } catch(Exception e){
            System.out.println(e);
            return "error";
        }
    }
}
