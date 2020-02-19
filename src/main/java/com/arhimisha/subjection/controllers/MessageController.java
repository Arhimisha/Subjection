package com.arhimisha.subjection.controllers;

import com.arhimisha.subjection.domain.Message;
import com.arhimisha.subjection.domain.Subject;
import com.arhimisha.subjection.domain.User;
import com.arhimisha.subjection.services.MessageService;
import com.arhimisha.subjection.services.SubjectService;
import com.arhimisha.subjection.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private final SubjectService subjectService;
    private final MessageService messageService;

    @Autowired
    public MessageController(UserService userService, SubjectService subjectService, MessageService messageService) {
        super(userService);
        this.subjectService = subjectService;
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public ModelAndView create(
            @RequestParam(name = "subjectId") long subjectId,
            @RequestParam(name = "text_message") String text,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final ModelAndView model = new ModelAndView();
        try {
            final Optional<User> user = this.checkUserDetails(userDetails);

            final Optional<Subject> subject = this.subjectService.loadById(subjectId);
            if (subject.isEmpty() || subject.get().isDeleted()) {
                return this.getErrorPage(userDetails, "Subject is not found");
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
            model.setViewName(String.format("redirect:/subject/%d?lastPage=true", subjectId));
            return model;
        } catch (UsernameNotFoundException e) {
            return this.getErrorPage(userDetails, e.getMessage());
        }

    }

    @PostMapping("/soft-delete")
    public ModelAndView softDelete(
            @RequestParam(name = "messageId") long messageId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final Optional<Message> message = this.messageService.loadById(messageId);
        if (message.isEmpty()) {
            return this.getErrorPage(userDetails, "This message is not existing anymore");
        }
        try {
            final Optional<User> user = this.checkUserDetails(userDetails);
            if (!user.get().isAdmin() || message.get().getAuthor() == null || user.get().getId() != message.get().getAuthor().getId()) {
                return this.getErrorPage(userDetails, "User don't have authority for deleting message");
            }
        } catch (UsernameNotFoundException e) {
            return this.getErrorPage(userDetails,e.getMessage());
        }
        this.messageService.softDelete(messageId);
        return new ModelAndView(String.format("redirect:/subject/%d", message.get().getSubject().getId()));
    }
}
