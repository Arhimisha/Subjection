package com.arhimisha.intech.controllers;


import org.springframework.web.servlet.ModelAndView;

public class BaseController {
    protected ModelAndView getErrorPage(String error) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("error", error);
        return model;
    }
}
