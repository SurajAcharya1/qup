package com.queueup.qup.controller.admin;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.dto.UserDto;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("userDto", new UserDto());
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
    public String deleteUserDetails(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("deleteMessage", "User Deleted Successfully!!!");
        return"redirect:/admin/usersList/"+userName;
    }

    @PostMapping("updateRole/{id}")
    public  String updateUserRole(@PathVariable("id") Integer id, UserDto userDto, RedirectAttributes redirectAttributes){
        try {
            userRepo.updateUserRole(userDto.getRole(),id);
            redirectAttributes.addFlashAttribute("updateMessage","User Role Updated Successfully!!!");
            return "redirect:/admin/usersList/"+userName;
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("updateMessage","Failed to Update User Role!!!");
            return "redirect:/admin/usersList/"+userName;
        }
    }
}
