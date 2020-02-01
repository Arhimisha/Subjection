package com.arhimisha.intech.controllers;

import com.arhimisha.intech.domain.Subject;
import com.arhimisha.intech.domain.User;
import com.arhimisha.intech.services.SubjectService;
import com.arhimisha.intech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.GregorianCalendar;
import java.util.Optional;

@Controller
@RequestMapping(value = "/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final UserService userService;

    @Autowired
    public SubjectController(SubjectService subjectService, UserService userService) {
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @GetMapping(value = "/{id:^[0-9]+$}")
    public ModelAndView getSubject(@PathVariable long id) {
        ModelAndView model = new ModelAndView("subject");

        Optional<Subject> subject = subjectService.loadById(id);
        if (subject.isEmpty() || subject.get().isDeleted()) {
            throw new RuntimeException("Subject is not found");
        }
        model.addObject("id", id);
        model.addObject("name", subject.get().getName());
        model.addObject("description", subject.get().getDescription());
        model.addObject("author", subject.get().getAuthor().getFullName());
        model.addObject("creationDate", subject.get().getCreationDate().getTime());
        return model;
    }

    @PostMapping(value = "/create")
    public String create(
            @RequestParam(name = "name", required = true) String name,
            @RequestParam(name = "description", defaultValue = "", required = false) String description,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (name.isBlank()) {
            throw new RuntimeException("Subject Name is empty");
        }

        final User user = (User) userService.loadUserByUsername(userDetails.getUsername());
        if (user == null) {
            throw new RuntimeException("User " + userDetails.getUsername() + " is not exist");
        }
        Subject subject = new Subject(
                0L,
                name,
                description,
                user,
                new GregorianCalendar(),
                false
        );
        subject = subjectService.save(subject);
        return "redirect:/subject/" + subject.getId();
    }

}
