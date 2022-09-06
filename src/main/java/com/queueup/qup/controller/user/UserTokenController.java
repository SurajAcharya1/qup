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

    @GetMapping("/{user_name}")
    public String openUserTokenPage(Model model, @PathVariable("user_name") String user_name){
        try{
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name)).equals("USER")){
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
                model.addAttribute("currentToken",tokenRepo.getCurrentUserTokenNumber());
                model.addAttribute("tokenNumber", tokenRepo.getTokenNumberByUsername(logInController.loggedInUserDetail.get(user_name)));
                if(tokenRepo.getTokenNumberByUsername(logInController.loggedInUserDetail.get(user_name))==null){
                    model.addAttribute("turn","You Do Not Have Any Token Assigned To Your Account.");
                }else{
                    if(tokenRepo.getCurrentUserTokenNumber()==tokenRepo.getTokenNumberByUsername(logInController.loggedInUserDetail.get(user_name))){
                        model.addAttribute("turn","Congrats, It's Your Turn Now.");
                        return "users/userToken";
                    }else{
                        model.addAttribute("turn", "Keep Patience, Your Turn Is About to Come.");
                        return "users/userToken";
                    }
                }
                return "users/userToken";
            }
            else{
                return "error";
            }
        }catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/absent/{token_number}/{user_name}")
    public String setStatusToAbsent(@PathVariable("token_number") Integer token_number,@PathVariable("user_name") String user_name){
            tokenRepo.setUserStatusToAbsent(token_number);
            tokenRepo.setStatusChangedByUser(token_number);
            return "redirect:/user/userPanel/"+user_name;
    }

    @GetMapping("/cancel/{token_number}/{user_name}")
    public String setStatusToCancelled(@PathVariable("token_number") Integer token_number, @PathVariable("user_name") String user_name){
            tokenRepo.setUserStatusToCancelled(token_number);
            tokenRepo.setStatusChangedByUser(token_number);
            return "redirect:/user/userPanel/"+user_name;
    }
}
