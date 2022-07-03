package com.queueup.qup.controller.admin;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.dto.KeyDto;
import com.queueup.qup.repository.KeyRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.impl.KeyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/key")
public class AdminKeyController {
    private final KeyServiceImpl keyService;
    @Autowired
    LogInController logInController;
    @Autowired
    UserRepo userRepo;

    @Autowired
    KeyRepo keyRepo;
    public AdminKeyController(KeyServiceImpl keyService) {

        this.keyService = keyService;
    }


    @GetMapping
    public String openAdminKeyPage(Model model){
        try{
            if(userRepo.getRoleByID(logInController.loggedInUserid).equals("ADMIN")) {
                model.addAttribute("keyDto", new KeyDto());
                model.addAttribute("keyList",keyService.findAll());
                model.addAttribute("userName",userRepo.findNameById(logInController.loggedInUserid));
                return "admin/key";
            }else {
               return "error";
            }
        } catch(Exception e){
            System.out.println(e);
            return "error";
        }
    }

    @GetMapping("delete/{key_id}")
    public String deleteKey(@PathVariable("key_id") Integer key_id){
        keyService.deleteById(key_id);
        return "redirect:/admin/key";
    }

    Integer Key_id;
    @GetMapping("update/{key_id}")
    public String updateKey(@PathVariable("key_id") Integer key_id){
        try {
            Key_id=key_id;
            return "redirect:/admin/key/#modal-update";
        }catch(Exception e){
            System.out.println(e);
            return "redirect:/admin/key";
        }
    }


    @PostMapping("create")
    public String createKey(@ModelAttribute KeyDto keyDto, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        try {
            keyDto = keyService.save(keyDto);
            redirectAttributes.addFlashAttribute("message","Key Generated Successfully!!!");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message","Failed to Generate Key!!!");
            e.fillInStackTrace();
            return "redirect:/admin/key";
        }
        return "redirect:/admin/key";
    }

    String Name;
    String Key;

    @PostMapping("update")
    public  String UpdateKey(KeyDto keyDto, RedirectAttributes redirectAttributes){
        try {
            keyRepo.updateKey(keyDto.getName(), keyDto.getKey(), Key_id);
            redirectAttributes.addFlashAttribute("updateMessage", "Key Updated Successfully!!!");
            return "redirect:/admin/key";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("updateMessage", "Could not Update Key");
            return "redirect:/admin/key";
        }
    }

}
