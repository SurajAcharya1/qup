package com.queueup.qup.controller.admin;


import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/account")
public class AdminAccountController {
    @Autowired
    LogInController logInController;
    @Autowired
    UserRepo userRepo;


    @GetMapping("/{user_name}")
    public String openAdminAccountPage(Model model, @PathVariable("user_name") String user_name){
        try{
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name)).equals("ADMIN")) {
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
                model.addAttribute("adminDetails",userRepo.getUserDetailsByUserName(logInController.loggedInUserDetail.get(user_name)));
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
