package com.arhimisha.intech.controllers;

import com.arhimisha.intech.dto.Message;
import com.arhimisha.intech.dto.Subject;
import com.arhimisha.intech.dto.User;
import com.arhimisha.intech.services.MessageService;
import com.arhimisha.intech.services.SubjectService;
import com.arhimisha.intech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.GregorianCalendar;
import java.util.Optional;

@Controller
@RequestMapping(value = "/message")
public class MessageController {

    private final UserService userService;
    private final SubjectService subjectService;
    private final MessageService messageService;

    @Autowired
    public MessageController(UserService userService, SubjectService subjectService, MessageService messageService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public String create(
            @RequestParam(name = "subjectId") long subjectId,
            @RequestParam(name = "text_message") String text,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final Optional<User> user = this.userService.findUserByUsername(userDetails.getUsername());
        if(user.isEmpty() || !user.get().isEnabled()){
            throw new RuntimeException("User " + userDetails.getUsername() + " is not exist");
        }
        final Optional<Subject> subject = this.subjectService.loadById(subjectId);
        if (subject.isEmpty() || subject.get().isDeleted()) {
            throw new RuntimeException("Subject is not found");
        }
        Message message = new Message(
            0L,
            text,
            subject.get(),
            user.get(),
            new GregorianCalendar(),
            false
        );
        messageService.save(message);
        return "redirect:/subject/"+subjectId;
    }
}
