package com.arhimisha.intech.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @GetMapping
    public ModelAndView admin(@AuthenticationPrincipal UserDetails userDetails){
        ModelAndView model = new ModelAndView("admin");
        model.addObject("currentUser", userDetails);
        return model;
    }
}
