package com.arhimisha.intech.controllers;

import com.arhimisha.intech.domain.User;
import com.arhimisha.intech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public ModelAndView main(@AuthenticationPrincipal UserDetails userDetails) {
        final ModelAndView modelAndView = new ModelAndView("main");
        if(userDetails != null){
            try {
                final UserDetails user =  userService.loadUserByUsername(userDetails.getUsername());
                if(user != null && user instanceof User){
                    modelAndView.addObject("userFullName",((User)user).getFullName());
                }
            } catch (UsernameNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return modelAndView;
    }
}
