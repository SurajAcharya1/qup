package com.queueup.qup.controller.user;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.TokenHistoryRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.impl.TokenHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{user_name}")
    public String openUserHistoryPage(Model model, @PathVariable("user_name") String user_name){
        try{
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name))==null){
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
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

