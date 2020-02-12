package com.arhimisha.intech.controllers;

import com.arhimisha.intech.registration.RegistrationDetails;
import com.arhimisha.intech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController extends BaseController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(){
        return "registration";
    }

    @PostMapping
    public String registerUser(RegistrationDetails registrationDetails, Model model){
        try{
            this.userService.registrationUser(registrationDetails);
        } catch (RuntimeException e){
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }
}
