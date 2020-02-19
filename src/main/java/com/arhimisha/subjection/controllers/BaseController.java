package com.arhimisha.subjection.controllers;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {
    protected ModelAndView getErrorPage(UserDetails userDetails, String error) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("error", error);
        model.addObject("currentUser", userDetails);
        return model;
    }
}
