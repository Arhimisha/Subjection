package com.arhimisha.subjection.controllers;


import com.arhimisha.subjection.domain.User;
import com.arhimisha.subjection.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public class BaseController {

    protected final UserService userService;

    public BaseController(UserService userService) {
        this.userService = userService;
    }

    protected ModelAndView getErrorPage(UserDetails userDetails, String error) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("error", error);
        model.addObject("currentUser", userDetails);
        return model;
    }

    protected Optional<User> checkUserDetails(UserDetails userDetails) throws UsernameNotFoundException{
        if (userDetails != null) {
            Optional<User> user = this.userService.findUserByUsername(userDetails.getUsername());
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User " + userDetails.getUsername() + " is not exist");
            }
            if (user.isPresent() && !user.get().isEnabled()) {
                throw new UsernameNotFoundException("User " + userDetails.getUsername() + " is blocked");
            }
            return user;
        }
        else{
            throw new UsernameNotFoundException("User is not exist");
        }
    }
}
