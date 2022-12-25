package com.queueup.qup.controller.user;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.dto.KeyDto;
import com.queueup.qup.dto.LoginDto;
import com.queueup.qup.dto.TokenDto;
import com.queueup.qup.dto.TokenHistoryDto;
import com.queueup.qup.repository.KeyRepo;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.EmailSenderService;
import com.queueup.qup.service.TokenHistoryService;
import com.queueup.qup.service.impl.TokenHistoryServiceImpl;
import com.queueup.qup.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/userPanel")
public class UserController{

    @Autowired
    UserRepo userRepo;
    @Autowired
    KeyRepo keyRepo;//used to get id of user

    @Autowired
    TokenRepo tokenRepo;// used to get id of token

    @Autowired
    LogInController logInController;

    @Autowired
    @Lazy
    TokenHistoryServiceImpl tokenHistoryService;

    @Autowired
    EmailSenderService senderService;
    private final TokenServiceImpl tokenService;

    public UserController(@Lazy TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }
    @GetMapping("/{user_name}")
    public String openUserPanelPage(@PathVariable("user_name") String user_name,Model model){
        try{
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name)).equals("USER")){
//                logInController.loggedInUserDetail.put(user_name,logInController.userName);
                model.addAttribute("tokenNumber",tokenRepo.getTokenNumberByUsername(logInController.loggedInUserDetail.get(user_name)));
                model.addAttribute("key",keyRepo.getKey());
                model.addAttribute("tokenDto", new TokenDto());
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
                logInController.userName=null;
                return "users/userPanel";
            }
            else{
                return "error";
            }
        }catch (Exception e){
            return "error";
        }
    }

    public String UserName;
    @PostMapping("create/{user_name}")
    public String createToken(@ModelAttribute TokenDto tokenDto, TokenHistoryDto tokenHistoryDto,RedirectAttributes redirectAttributes, @PathVariable("user_name") String userName){
        String key = tokenDto.getToken_key();
        UserName=userName;
        try {
            if(keyRepo.getKeyFromLogin(key).equals(key)) {
                tokenDto = tokenService.save(tokenDto);
                tokenHistoryDto = tokenHistoryService.save(tokenHistoryDto);
                tokenRepo.deleteTokenView();
                tokenRepo.createTokenView();
                redirectAttributes.addFlashAttribute("tokenMessage", "Token Generated Successfully!!!");
            }
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("tokenMessage","Token Generation Failed");
            e.fillInStackTrace();
            return "redirect:/user/userPanel/"+userName;
        }
        return "redirect:/user/userPanel/"+userName;
    }

    @GetMapping("/absent/{token_number}/{user_name}")
    public String setStatusToAbsent(@PathVariable("token_number") Integer token_number,@PathVariable("user_name") String user_name){
            tokenRepo.setUserStatusToAbsent(token_number);
            tokenRepo.setStatusChangedByUser(token_number);
            return "redirect:/user/userPanel/"+user_name;
    }

    @GetMapping("/cancel/{token_number}/{user_name}")
    public String setStatusToCancelled(@PathVariable("token_number") Integer token_number,@PathVariable("user_name") String user_name) {
            tokenRepo.setUserStatusToCancelled(token_number);
            tokenRepo.setStatusChangedByUser(token_number);
            return "redirect:/user/userPanel/"+user_name;
    }
}
