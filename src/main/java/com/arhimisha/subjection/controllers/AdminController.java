package com.arhimisha.subjection.controllers;

import com.arhimisha.subjection.domain.User;
import com.arhimisha.subjection.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    public AdminController(UserService userService) {
        super(userService);
    }

    @GetMapping
    public ModelAndView admin(@AuthenticationPrincipal UserDetails userDetails){
        ModelAndView model = new ModelAndView("admin");
        try {
            final Optional<User> user = this.checkUserDetails(userDetails);
            model.addObject("currentUser", user.get());
        } catch (UsernameNotFoundException e) {
            return this.getErrorPage(userDetails,e.getMessage());
        }
        return model;
    }
}
