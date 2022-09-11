package com.epam.finalproject.controller;

import com.epam.finalproject.dto.RoleDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.service.UserService;
import com.epam.finalproject.util.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/profile")
    @PreAuthorize("isAuthenticated()")
    String profilePage(Model model,@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id) {
        UserDTO user = userService.findById(id);
        log.trace("User {}", user);
        if (SecurityUtils.hasRole(userDetails, "CUSTOMER") && isCustomer(user) && !user.getUsername()
                .equals(userDetails.getUsername())) {
            throw new AccessDeniedException(String.format("Not permit another user page %s", userDetails));
        }
        model.addAttribute("user", user);
        return "user";
    }

    private boolean isCustomer(UserDTO user) {
        return user.getRoles().stream().map(RoleDTO::getName).anyMatch(s -> s.equals("CUSTOMER"));
    }

}
