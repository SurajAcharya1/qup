package com.queueup.qup.controller.admin;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/adminPanel")
public class AdminController{

    private final TokenServiceImpl tokenService;

    @Autowired
    UserRepo userRepo;
    @Autowired
    LogInController logInController;
    @Autowired
    TokenRepo tokenRepo;

    public AdminController(TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping
    public String openAdminPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid).equals("ADMIN")) {
                model.addAttribute("totalToken",tokenRepo.findAll().size());
                model.addAttribute("totalUser", userRepo.totalUsers());
                model.addAttribute("tokenList",tokenService.findAll());
                model.addAttribute("remainingToken",tokenRepo.getRemainingTokenCount());
                model.addAttribute("currentToken",tokenRepo.getCurrentUserTokenNumber());
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                return "admin/adminPanel";
            }else {
                return "error";
            }
        } catch(Exception e){
            System.out.println(e);
            return "error";
        }
    }


}
