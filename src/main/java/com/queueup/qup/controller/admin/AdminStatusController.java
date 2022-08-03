package com.queueup.qup.controller.admin;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.EmailSenderService;
import com.queueup.qup.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/status")
public class AdminStatusController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenRepo tokenRepo;

    @Autowired
    LogInController logInController;

    @Autowired
    private EmailSenderService senderService;
    private final TokenServiceImpl tokenService;

    public AdminStatusController(TokenServiceImpl tokenService) {

        this.tokenService = tokenService;
    }

    Integer tokenGap=3;    //Define the token Gap Here


    @GetMapping("/{user_name}")
    public String openAdminStatusPage(Model model, @PathVariable("user_name") String user_name) {
        tokenRepo.deleteTokenView();
        tokenRepo.createTokenView();
        try {
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name)).equals("ADMIN")) {
                model.addAttribute("totalToken", tokenService.findAll().size());
                model.addAttribute("tokenList", tokenService.findAll());
                model.addAttribute("currentToken", tokenRepo.getCurrentUserTokenNumber());
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
                return "admin/status";
            } else {
                return "error";
            }
        } catch (Exception e) {
            System.out.println(e);
            return "error";
        }
    }

    @GetMapping("/delete")
    public String deleteAllTokens() {
        tokenRepo.deleteAllTokens();
        return "redirect:/admin/status";
    }

    @GetMapping("/finish/{token_number}")
    public String setStatusToComplete(@PathVariable("token_number") Integer token_number, RedirectAttributes redirectAttributes) {
        try {
            senderService.sendEmail(tokenRepo.getEmailFromView(tokenGap),
                    "Queue Notification",
                    "Your turn Is About to come please get to Queue as soon as possible.");
            tokenRepo.setUserStatustoComplete(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("mail","Could not send Mail");
            tokenRepo.setUserStatustoComplete(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status";
        }
    }

    @GetMapping("/absent/{token_number}")
    public String setStatusToAbsent(@PathVariable("token_number") Integer token_number, RedirectAttributes redirectAttributes) {
        try {
            senderService.sendEmail(tokenRepo.getEmailFromView(tokenGap),
                    "Queue Notification",
                    "Your turn Is About to come please get to Queue as soon as possible.");
            tokenRepo.setUserStatusToAbsent(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("mail","Could not send Mail");
            tokenRepo.setUserStatusToAbsent(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status";
        }
    }

    @GetMapping("/cancel/{token_number}")
    public String setStatusToCancelled(@PathVariable("token_number") Integer token_number, RedirectAttributes redirectAttributes) {
        try {
            senderService.sendEmail(tokenRepo.getEmailFromView(tokenGap),
                    "Queue Notification",
                    "Your turn Is About to come please get to Queue as soon as possible.");
            tokenRepo.setUserStatusToCancelled(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("mail","Could not send Mail");
            tokenRepo.setUserStatusToCancelled(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status";
        }
    }
}