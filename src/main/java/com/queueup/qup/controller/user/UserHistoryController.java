package com.queueup.qup.controller.user;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.TokenHistoryRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.impl.TokenHistoryServiceImpl;
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
    TokenHistoryRepo tokenHistoryRepo;
    @Autowired
    LogInController logInController;
    private final TokenHistoryServiceImpl tokenHistoryService;

    public UserHistoryController(TokenHistoryServiceImpl tokenHistoryService) {
        this.tokenHistoryService = tokenHistoryService;
    }

    @GetMapping
    public String openUserHistoryPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid)==null){
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                model.addAttribute("historyList",tokenHistoryRepo.getUserHistory(logInController.loggedInUserid));
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

