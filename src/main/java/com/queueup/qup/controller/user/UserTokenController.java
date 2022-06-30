package com.queueup.qup.controller.user;


import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/token")
public class UserTokenController{

    @Autowired
    UserRepo userRepo;
    @Autowired
    LogInController logInController;
    @Autowired
    TokenRepo tokenRepo;

    @Autowired
    EmailSenderService senderService;

    @GetMapping
    public String openUserTokenPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid)==null){
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                model.addAttribute("currentToken",tokenRepo.getCurrentUserTokenNumber());
                model.addAttribute("tokenNumber", tokenRepo.getTokenNumber(logInController.loggedInUserid));
                return "users/userToken";
            }
            else{
                return "error";
            }
        }catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/absent/{token_number}")
    public String setStatusToAbsent(@PathVariable("token_number") Integer token_number,Model model){
        try {
            senderService.sendEmail(tokenRepo.getEmailFromTokenNumber(token_number + 1),
                    "Queue Notification",
                    "Your turn Is About to come please get to Queue as soon as possible.");
            tokenRepo.setUserStatusToAbsent(token_number);
            tokenRepo.setStatusChangedByUser(token_number);
            return "redirect:/user/userPanel";
        }catch (Exception e){
            model.addAttribute("mail","Could not send Mail");
            tokenRepo.setUserStatusToAbsent(token_number);
            tokenRepo.setStatusChangedByUser(token_number);
            return "redirect:/user/userPanel";
        }
    }

    @GetMapping("/cancel/{token_number}")
    public String setStatusToCancelled(@PathVariable("token_number") Integer token_number, Model model){
        try {
            senderService.sendEmail(tokenRepo.getEmailFromTokenNumber(token_number + 1),
                    "Queue Notification",
                    "Your turn Is About to come please get to Queue as soon as possible.");
            tokenRepo.setUserStatusToCancelled(token_number);
            tokenRepo.setStatusChangedByUser(token_number);
            return "redirect:/user/userPanel";
        }catch (Exception e){
            model.addAttribute("mail","Could not send Mail");
            tokenRepo.setUserStatusToCancelled(token_number);
            tokenRepo.setStatusChangedByUser(token_number);
            return "redirect:/user/userPanel";
        }
    }
}
