package com.arhimisha.subjection.controllers;

import com.arhimisha.subjection.domain.Subject;
import com.arhimisha.subjection.domain.User;
import com.arhimisha.subjection.repository.SubjectRepository;
import com.arhimisha.subjection.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class MainController extends  BaseController {

    private final SubjectRepository subjectRepository;
    private final int PAGE_SIZE = 10;

    @Autowired
    public MainController(UserService userService,
                          SubjectRepository subjectRepository) {
        super(userService);
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/")
    public ModelAndView main(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        final ModelAndView model = new ModelAndView("main");
        if (userDetails != null) {
            try {
                final Optional<User> user = this.checkUserDetails(userDetails);
                model.addObject("userFullName", user.get().getFullName());
                model.addObject("currentUser", user.get());
            } catch (UsernameNotFoundException e) {
                return this.getErrorPage(userDetails,e.getMessage());
            }
        }

        if (pageNumber < 0) {
            return this.getErrorPage(userDetails, String.format("Page number %d is not exist", pageNumber));
        }

        final Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        final Page<Subject> subjectsPage = this.subjectRepository.getAllSortedByMessagesCreationDate(pageable);
        if (pageNumber != 0 && pageNumber > (subjectsPage.getTotalPages()-1)) {
            return this.getErrorPage(userDetails, String.format("Page number %d is not exist", pageNumber));
        }
        model.addObject("subjects", subjectsPage);
        model.addObject("currentPage", pageNumber);
        model.addObject("totalPages", subjectsPage.getTotalPages());

        return model;
    }
}
