package com.arhimisha.intech.controllers;

import com.arhimisha.intech.registration.RegistrationDetails;
import com.arhimisha.intech.registration.exceptions.ComplianceRequirementsException;
import com.arhimisha.intech.registration.exceptions.EmailAlreadyExistsException;
import com.arhimisha.intech.registration.exceptions.PasswordConfirmException;
import com.arhimisha.intech.registration.exceptions.UserAlreadyExistsException;
import com.arhimisha.intech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView registerUser(RegistrationDetails registrationDetails){
        ModelAndView model =new ModelAndView();
        model.addObject("registrationDetails", registrationDetails);
        try{
            this.userService.registrationUser(registrationDetails);
            model.setViewName("redirect:/login");
        } catch (RuntimeException e){
            model.addObject("error", e.getMessage());
            model.setViewName("error");
        } catch (UserAlreadyExistsException e) {
            model.addObject("LoginError", "User with this login already exist");
            model.setViewName("registration");
        } catch (PasswordConfirmException e) {
            model.addObject("PasswordConfirmError", "Password is not confirmed");
            model.setViewName("registration");
        } catch (EmailAlreadyExistsException e) {
            model.addObject("EmailError","User with this email already exists");
            model.setViewName("registration");
        } catch (ComplianceRequirementsException e) {
            model.addObject("PasswordComplianceError", "Password is not compliant with requirements");
            model.setViewName("registration");
        }
        finally {
            return model;
        }
    }
}
