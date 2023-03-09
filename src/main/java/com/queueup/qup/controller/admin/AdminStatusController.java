package com.queueup.qup.controller.admin;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.dto.TokenGapDto;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.EmailSenderService;
import com.queueup.qup.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


    private final EmailSenderService senderService;
    private final TokenServiceImpl tokenService;

    public AdminStatusController(EmailSenderService senderService, TokenServiceImpl tokenService) {
        this.senderService = senderService;

        this.tokenService = tokenService;
    }

    Integer tokenGap=10;    //Define the token gap Here
    /*
     tokenGap determines how many token/s will be skipped
     for example
     if tokenGap =2 and there are following tokens:
     1,2,3,4,5
     and token no. 1 is currently being processed(it's token no. 1's turn)
     then mail will be sent to token no. 4
     when token no. 1 will be finished processing
    */


    @GetMapping("/{user_name}")
    public String openAdminStatusPage(Model model, @PathVariable("user_name") String user_name) {
        tokenRepo.deleteTokenView();
        tokenRepo.createTokenView();
        try {
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name)).equals("ADMIN")) {
                model.addAttribute("totalToken", tokenService.findAll().size());
                model.addAttribute("tokenGapDto", new TokenGapDto());
                model.addAttribute("tokenList", tokenService.findAll());
                model.addAttribute("currentToken", tokenRepo.getCurrentUserTokenNumber());
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
                model.addAttribute("tokenGap",tokenGap);
                return "admin/status";
            } else {
                return "error";
            }
        } catch (Exception e) {
            System.out.println(e);
            return "error";
        }
    }

    @GetMapping("/delete/{user_name}")
    public String deleteAllTokens(@PathVariable("user_name") String user_name) {
        tokenRepo.deleteAllTokens();
        return "redirect:/admin/status/"+user_name;
    }

    @GetMapping("/finish/{token_number}/{user_name}")
    public String setStatusToComplete(@PathVariable("token_number") Integer token_number, @PathVariable("user_name") String user_name, RedirectAttributes redirectAttributes) {
        try {
            senderService.sendEmail(tokenRepo.getEmailFromView(tokenGap),
                    "Queue Notification",
                    "Your turn is about to come please get to Queue as soon as possible. \n\n\n\n" +
                            "Tokens before your token number: "+tokenGap);
            tokenRepo.setUserStatustoComplete(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status/"+user_name;
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("mail","Could not send Mail");
            tokenRepo.setUserStatustoComplete(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            System.out.println(e);
            return "redirect:/admin/status/"+user_name;
        }
    }

    @GetMapping("/absent/{token_number}/{user_name}")
    public String setStatusToAbsent(@PathVariable("token_number") Integer token_number,@PathVariable("user_name") String user_name, RedirectAttributes redirectAttributes) {
        try {
            senderService.sendEmail(tokenRepo.getEmailFromView(tokenGap),
                    "Queue Notification",
                    "Your turn is about to come please get to Queue as soon as possible. \n\n\n\n" +
                            "Tokens before your token number: "+tokenGap);
            tokenRepo.setUserStatusToAbsent(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status/"+user_name;
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("mail","Could not send Mail");
            tokenRepo.setUserStatusToAbsent(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status/"+user_name;
        }
    }

    @GetMapping("/cancel/{token_number}/{user_name}")
    public String setStatusToCancelled(@PathVariable("token_number") Integer token_number, @PathVariable("user_name") String user_name, RedirectAttributes redirectAttributes) {
        try {
            senderService.sendEmail(tokenRepo.getEmailFromView(tokenGap),
                    "Queue Notification",
                    "Your turn is about to come please get to Queue as soon as possible. \n\n\n\n" +
                            "Tokens before your token number: "+tokenGap);
            tokenRepo.setUserStatusToCancelled(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status/"+user_name;
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("mail","Could not send Mail");
            tokenRepo.setUserStatusToCancelled(token_number);
            tokenRepo.setStatusChangedByAdmin(token_number);
            return "redirect:/admin/status/"+user_name;
        }
    }

    @PostMapping("update/{user_name}")
    public String updateTokenGap(TokenGapDto tokenGapDto, RedirectAttributes redirectAttributes,@PathVariable String user_name){
        tokenGap=tokenGapDto.getTokenGap();
        return "redirect:/admin/status/"+user_name;
    }
}
