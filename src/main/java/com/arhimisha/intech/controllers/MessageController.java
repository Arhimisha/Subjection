package com.arhimisha.intech.controllers;

import com.arhimisha.intech.domain.Message;
import com.arhimisha.intech.domain.Subject;
import com.arhimisha.intech.domain.User;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.GregorianCalendar;
import java.util.Optional;

@Controller
@RequestMapping(value = "/message")
public class MessageController extends BaseController {

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
        return String.format("redirect:/subject/%d?lastPage=true", subjectId);
    }
    @PostMapping("/soft-delete")
    public ModelAndView softDelete(
            @RequestParam(name = "messageId") long messageId,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        final Optional<Message> message = this.messageService.loadById(messageId);
        if (message.isEmpty()){
            return this.getErrorPage("This message is not existing anymore");
        }
        final Optional<User> user = this.userService.findUserByUsername(userDetails.getUsername());
        if (user.isEmpty() || !user.get().isEnabled()) {
            return this.getErrorPage("Current User is not enable or not exist");
        }
        if(!user.get().isAdmin() || message.get().getAuthor() == null || user.get().getId() != message.get().getAuthor().getId()){
            return this.getErrorPage("User don't have authority for deleting message");
        }
        this.messageService.softDelete(messageId);
        return new ModelAndView(String.format("redirect:/subject/%d",message.get().getSubject().getId()));
    }
}
