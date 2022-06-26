package com.queueup.qup.controller;

import com.queueup.qup.dto.LoginDto;
import com.queueup.qup.dto.UserDto;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class IndexController {

    private final UserServiceImpl userService;
    @Autowired
    UserRepo userRepo;

    @Autowired
    LogInController logInController;
    public IndexController(UserServiceImpl userService) {

        this.userService = userService;
    }

    @GetMapping()
    public String openMainPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        logInController.loggedInUserid=null;
        return "index";

    }

    @PostMapping("create")
    public String createUser(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes){
       try {
           userDto = userService.save(userDto);
           redirectAttributes.addFlashAttribute("message","User Registered successfully!!!");
       }catch (Exception e) {
           redirectAttributes.addFlashAttribute("message","User Registration Failed");
           e.fillInStackTrace();
           return "redirect:/#form-modal";
       }
    return "redirect:/#form-modal";
    }

}
