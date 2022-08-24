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

    public String userName;
    public AdminKeyController(KeyServiceImpl keyService) {

        this.keyService = keyService;
    }


    @GetMapping("/{user_name}")
    public String openAdminKeyPage(Model model, @PathVariable("user_name") String user_name){
        userName=user_name;
        try{
            if(userRepo.getRoleByUserName(logInController.loggedInUserDetail.get(user_name)).equals("ADMIN")) {
                model.addAttribute("keyDto", new KeyDto());
                model.addAttribute("keyList",keyService.findAll());
                model.addAttribute("userName",logInController.loggedInUserDetail.get(user_name));
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
        return "redirect:/admin/key/"+userName;
    }

    Integer Key_id;
    @GetMapping("update/{key_id}")
    public String updateKey(@PathVariable("key_id") Integer key_id,RedirectAttributes redirectAttributes){
        try {
            Key_id=key_id;
            redirectAttributes.addFlashAttribute("keyName",keyRepo.getKeyNamebyId(Key_id));
            redirectAttributes.addFlashAttribute("key",keyRepo.getKeybyId(Key_id));
            redirectAttributes.addFlashAttribute("keyId",Key_id);
            return "redirect:/admin/key/"+userName+"/#modal-update";
        }catch(Exception e){
            System.out.println(e);
            return "redirect:/admin/key/"+userName;
        }
    }


    @PostMapping("create")
    public String createKey(@ModelAttribute KeyDto keyDto, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        try {
            keyDto = keyService.save(keyDto);
            redirectAttributes.addFlashAttribute("message","Key Generated Successfully!!!");
            return "redirect:/admin/key/"+userName;
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message","Failed to Generate Key!!!");
            e.fillInStackTrace();
            return "redirect:/admin/key/"+userName;
        }
    }

    String Name;
    String Key;

    @PostMapping("update")
    public  String UpdateKey(KeyDto keyDto, RedirectAttributes redirectAttributes){
        try {
            keyRepo.updateKey(keyDto.getName(), keyDto.getKey(), Key_id);
            redirectAttributes.addFlashAttribute("updateMessage", "Key Updated Successfully!!!");
            return "redirect:/admin/key/"+userName;
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("updateMessage", "Could not Update Key");
            return "redirect:/admin/key/"+userName;
        }
    }

}
