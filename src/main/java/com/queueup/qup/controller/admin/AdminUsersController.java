package com.queueup.qup.controller.admin;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/usersList")
public class AdminUsersController{
    private final UserServiceImpl userService;
    @Autowired
    UserRepo userRepo;

    @Autowired
    LogInController logInController;

    public String userName;
    public AdminUsersController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping("/{user_name}")
    public String openUserPage(Model model, @PathVariable("user_name") String user_name){
        userName=user_name;
        try{
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name)).equals("ADMIN")) {
                model.addAttribute("userList",userService.findAll());
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
                return "admin/users";
            }else {
                return "error";
            }
        } catch(Exception e){
            System.out.println(e);
            return "error";
        }
    }
    @GetMapping("delete/{id}")
    public String deleteUserDetails(@PathVariable("id") Integer id){
        userService.deleteById(id);
        return"redirect:/admin/usersList/"+userName;
    }
}
