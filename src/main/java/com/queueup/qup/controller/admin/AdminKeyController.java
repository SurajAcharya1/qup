package com.queueup.qup.controller.admin;

import com.queueup.qup.dto.KeyDto;
import com.queueup.qup.service.impl.KeyServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/key")
public class AdminKeyController {
    private final KeyServiceImpl keyService;

    public AdminKeyController(KeyServiceImpl keyService) {
        this.keyService = keyService;
    }


    @GetMapping
    public String openAdminKeyPage(Model model){
        model.addAttribute("keyDto", new KeyDto());
        model.addAttribute("keyList",keyService.findAll());
        return "admin/key";
    }

    @PostMapping("/create")
    public String createKey(@ModelAttribute KeyDto keyDto, RedirectAttributes redirectAttributes){
        keyDto = keyService.save(keyDto);
        if(keyDto != null){
            redirectAttributes.addFlashAttribute("message","Key updated Successfully");
        }else{
            redirectAttributes.addFlashAttribute("message","Failed to Update Key");
        }
        return "redirect:/admin/key";
    }
}
