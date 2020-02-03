package com.arhimisha.intech.controllers;

import com.arhimisha.intech.dto.Subject;
import com.arhimisha.intech.dto.User;
import com.arhimisha.intech.services.MessageService;
import com.arhimisha.intech.services.SubjectService;
import com.arhimisha.intech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private final MessageService messageService;

    @Autowired
    public SubjectController(SubjectService subjectService, UserService userService, MessageService messageService) {
        this.subjectService = subjectService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping(value = "/{id:^[0-9]+$}")
    public ModelAndView getSubject(@PathVariable long id) {
        ModelAndView model = new ModelAndView("subject");

        Optional<Subject> subject = this.subjectService.loadById(id);
        if (subject.isEmpty() || subject.get().isDeleted()) {
            throw new RuntimeException("Subject is not found");
        }
        model.addObject("id", id);
        model.addObject("name", subject.get().getName());
        model.addObject("description", subject.get().getDescription());
        model.addObject("author", subject.get().getAuthor().getFullName());
        model.addObject("creationDate", subject.get().getCreationDate().getTime());
        model.addObject(
                "messages",
                this.messageService.loadAllBySubjectAndDeleted(
                        subject.get(),
                        false,
                        Sort.by(Sort.Direction.ASC, "creationDate")
                )
        );
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

        final Optional<User> user = this.userService.findUserByUsername(userDetails.getUsername());
        if (user.isEmpty() || !user.get().isEnabled()) {
            throw new RuntimeException("User " + userDetails.getUsername() + " is not exist");
        }
        Subject subject = new Subject(
                0L,
                name,
                description,
                user.get(),
                new GregorianCalendar(),
                false
        );
        subject = this.subjectService.save(subject);
        return "redirect:/subject/" + subject.getId();
    }
}
