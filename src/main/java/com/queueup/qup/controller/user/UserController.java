package com.queueup.qup.controller.user;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.dto.KeyDto;
import com.queueup.qup.dto.LoginDto;
import com.queueup.qup.dto.TokenDto;
import com.queueup.qup.dto.TokenHistoryDto;
import com.queueup.qup.repository.KeyRepo;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.TokenHistoryService;
import com.queueup.qup.service.impl.TokenHistoryServiceImpl;
import com.queueup.qup.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    TokenHistoryServiceImpl tokenHistoryService;
    private final TokenServiceImpl tokenService;

    public UserController(TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }
    @GetMapping
    public String openUserPanelPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid)==null){
                model.addAttribute("tokenNumber",tokenRepo.getTokenNumber(logInController.loggedInUserid));
                model.addAttribute("key",keyRepo.getKey());
                model.addAttribute("tokenDto", new TokenDto());
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                return "users/userPanel";
            }
            else{
                return "error";
            }
        }catch (Exception e){
            return "error";
        }
    }

    @PostMapping("create")
    public String createUser(@ModelAttribute TokenDto tokenDto, TokenHistoryDto tokenHistoryDto,RedirectAttributes redirectAttributes){
        String key = tokenDto.getToken_key();
        try {
            if(keyRepo.getKeyFromLogin(key).equals(key)) {
                tokenDto = tokenService.save(tokenDto);
                tokenHistoryDto = tokenHistoryService.save(tokenHistoryDto);
                redirectAttributes.addFlashAttribute("tokenMessage", "Token Generated Successfully!!!");
            }
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("tokenMessage","Token Generation Failed");
            e.fillInStackTrace();
            return "redirect:/user/userPanel";
        }
        return "redirect:/user/userPanel";
    }

    @GetMapping("/absent/{token_number}")
    public String setStatusToAbsent(@PathVariable("token_number") Integer token_number){
        tokenRepo.setUserStatusToAbsent(token_number);
        tokenRepo.setStatusChangedByUser(token_number);
        return "redirect:/user/userPanel";
    }

    @GetMapping("/cancel/{token_number}")
    public String setStatusToCancelled(@PathVariable("token_number") Integer token_number) {
        tokenRepo.setUserStatusToCancelled(token_number);
        tokenRepo.setStatusChangedByUser(token_number);
        return "redirect:/user/userPanel";
    }
}
