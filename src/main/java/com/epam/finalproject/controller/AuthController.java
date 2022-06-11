package com.epam.finalproject.controller;

import com.epam.finalproject.entity.User;
import com.epam.finalproject.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    UserService userService;

    @GetMapping("/signup")
    String signUpPage(@RequestParam Optional<String> usernameError,@RequestParam Optional<String> emailError, Model model) {
        usernameError.ifPresent( e -> model.addAttribute("usernameError", e));
        emailError.ifPresent( e -> model.addAttribute("emailError", e));
        return "signup";
    }

    @ModelAttribute("singUpForm")
    public SignUpForm signUpForm() {
        return new SignUpForm();
    }

    @PostMapping("/signup")
    String signUp(@ModelAttribute("singUpForm") @Valid SignUpForm form, Model model, RedirectAttributes redirectedAttributes) {
        boolean isErrorsExists = false;
        if (userService.existsByUsername(form.getUsername())) {
            isErrorsExists = true;
            redirectedAttributes.addAttribute("usernameError", "true");
        }
        if (userService.existsByEmail(form.getEmail())) {
            isErrorsExists = true;
            redirectedAttributes.addAttribute("emailError", "true");
        }
        if (isErrorsExists) {
            return "redirect:/auth/signup";
        }
        User user = userService.signUpNewUserAccount(form);
        log.info("Created user:" + user.toString());
        return "redirect:/auth/signin";
    }

    @GetMapping("/signin")
    String signInPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error!=null)
            model.addAttribute("error","true");
        return "signin";
    }

    @GetMapping("/signout")
    String signOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
