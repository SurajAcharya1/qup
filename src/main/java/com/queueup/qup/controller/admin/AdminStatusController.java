package com.queueup.qup.controller.admin;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/status")
public class AdminStatusController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenRepo tokenRepo;

    @Autowired
    LogInController logInController;
    private final TokenServiceImpl tokenService;

    public AdminStatusController(TokenServiceImpl tokenService) {

        this.tokenService = tokenService;
    }

    @GetMapping
    public String openAdminStatusPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid).equals("ADMIN")) {
                model.addAttribute("totalToken",tokenService.findAll().size());
                model.addAttribute("tokenList",tokenService.findAll());
                model.addAttribute("currentToken",tokenRepo.getCurrentUserTokenNumber());
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                return "admin/status";
            }else {
                return "error";
            }
        } catch(Exception e){
            System.out.println(e);
            return "error";
        }
    }

    @GetMapping("/delete")
    public String deleteAllTokens(){
        tokenRepo.deleteAllTokens();
        return "redirect:/admin/status";
    }

    @GetMapping("/finish/{token_number}")
    public String setStatusToComplete(@PathVariable("token_number") Integer token_number){
        tokenRepo.setUserStatustoComplete(token_number);
        return "redirect:/admin/status";
    }

    @GetMapping("/absent/{token_number}")
    public String setStatusToAbsent(@PathVariable("token_number") Integer token_number){
        tokenRepo.setUserStatusToAbsent(token_number);
        return "redirect:/admin/status";
    }

    @GetMapping("/cancel/{token_number}")
    public String setStatusToCancelled(@PathVariable("token_number") Integer token_number){
        tokenRepo.setUserStatusToCancelled(token_number);
        return "redirect:/admin/status";
    }
}

