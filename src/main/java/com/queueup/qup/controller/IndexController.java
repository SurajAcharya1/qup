package com.queueup.qup.controller;

import com.queueup.qup.dto.LoginDto;
import com.queueup.qup.dto.UserDto;
import com.queueup.qup.repository.KeyRepo;
import com.queueup.qup.repository.TokenRepo;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/")
public class IndexController {

    LocalDate localDate = LocalDate.now();
    private final UserServiceImpl userService;
    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenRepo tokenRepo;

    @Autowired
    KeyRepo keyRepo;

    @Autowired
    LogInController logInController;
    public IndexController(UserServiceImpl userService) {

        this.userService = userService;
    }

    @GetMapping()
    public String openMainPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
        model.addAttribute("date",dateFormatter.format(date));
        //logInController.loggedInUserid=null;
        try {
            tokenRepo.deleteByDate(localDate);
            keyRepo.deleteAllKey(localDate);
        }catch (Exception e){
            System.out.println(e);
        }
        return "index";

    }

    @PostMapping("create")
    public String createUser(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes){
       try {
           userDto = userService.save(userDto);
           redirectAttributes.addFlashAttribute("message","User Registered successfully !!!");
       }catch (Exception e) {
           redirectAttributes.addFlashAttribute("message","User Registration Failed !!!");
           e.fillInStackTrace();
           return "redirect:/#form-modal";
       }
    return "redirect:/#form-modal";
    }

}
