package com.arhimisha.subjection.controllers;

import com.arhimisha.subjection.domain.Message;
import com.arhimisha.subjection.domain.Subject;
import com.arhimisha.subjection.domain.User;
import com.arhimisha.subjection.services.MessageService;
import com.arhimisha.subjection.services.SubjectService;
import com.arhimisha.subjection.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.GregorianCalendar;
import java.util.Optional;

@Controller
@RequestMapping(value = "/subject")
public class SubjectController extends BaseController {

    private final SubjectService subjectService;
    private final MessageService messageService;
    private final int PAGE_SIZE = 10;

    @Autowired
    public SubjectController(SubjectService subjectService, UserService userService, MessageService messageService) {
        super(userService);
        this.subjectService = subjectService;
        this.messageService = messageService;
    }

    /**
     * If lastPage == true, it does not matter what is pageNumber
     */
    @GetMapping(value = "/{id:^[0-9]+$}")
    public ModelAndView getSubject(
            @PathVariable long id,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "lastPage", defaultValue = "false") boolean lastPage,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final ModelAndView model = new ModelAndView("subject");

        final Optional<Subject> subject = this.subjectService.loadById(id);
        if (subject.isEmpty() || subject.get().isDeleted()) {
            throw new RuntimeException("Subject is not found");
        }
        model.addObject("subject", subject.get());

        if (lastPage) {
            final long totalMessages = this.messageService.countAllBySubjectAndDeleted(subject.get(), false);
            pageNumber = (int) (totalMessages - 1) / this.PAGE_SIZE;
        }
        if (pageNumber < 0) {
            return this.getErrorPage(userDetails, String.format("Page number %d is not exist", pageNumber));
        }

        final Pageable pageable = PageRequest.of(pageNumber, this.PAGE_SIZE, Sort.by("creationDate").ascending());
        final Page<Message> messagesPage = this.messageService
                .loadAllBySubjectAndDeleted(subject.get(), false, pageable);
        if (pageNumber != 0 && pageNumber > (messagesPage.getTotalPages() - 1)) {
            return this.getErrorPage(userDetails, String.format("Page number %d is not exist", pageNumber));
        }
        model.addObject("messages", messagesPage.getContent());
        model.addObject("currentPage", pageNumber);
        model.addObject("totalPages", messagesPage.getTotalPages());

        try {
            final Optional<User> user = this.checkUserDetails(userDetails);
            model.addObject("currentUser", user.get());
        } catch (UsernameNotFoundException e) {
            return this.getErrorPage(userDetails,e.getMessage());
        }
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

    @PostMapping("/soft-delete")
    public ModelAndView softDelete(
            @RequestParam(name = "subjectId") long subjectId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final Optional<Subject> subject = this.subjectService.loadById(subjectId);
        if (subject.isEmpty()) {
            return this.getErrorPage(userDetails, "This subjectId is not existing anymore");
        }
        final Optional<User> user = this.userService.findUserByUsername(userDetails.getUsername());
        if (user.isEmpty() || !user.get().isEnabled()) {
            return this.getErrorPage(userDetails, "Current User is not enable or not exist");
        }
        if (!user.get().isAdmin() || subject.get().getAuthor() == null || user.get().getId() != subject.get().getAuthor().getId()) {
            return this.getErrorPage(userDetails, "User don't have authority for deleting message");
        }
        this.subjectService.softDelete(subjectId);
        return new ModelAndView(String.format("redirect:/"));
    }
}
