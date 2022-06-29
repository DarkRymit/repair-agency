package com.epam.finalproject.controller;

import com.epam.finalproject.entity.User;
import com.epam.finalproject.entity.VerificationToken;
import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.registration.OnRegistrationCompleteEvent;
import com.epam.finalproject.service.UserService;
import com.epam.finalproject.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    VerificationTokenService verificationTokenService;

    ApplicationEventPublisher eventPublisher;

    @GetMapping("/signup")
    String signUpPage(@RequestParam Optional<String> usernameError,@RequestParam Optional<String> emailError, Model model) {
        usernameError.ifPresent( e -> model.addAttribute("usernameError", e));
        emailError.ifPresent( e -> model.addAttribute("emailError", e));
        return "signup";
    }

    @ModelAttribute("singUpForm")
    public SignUpRequest signUpForm() {
        return new SignUpRequest();
    }

    @PostMapping("/signup")
    String signUp(@ModelAttribute("singUpForm") @Valid SignUpRequest form, Model model, RedirectAttributes redirectedAttributes, HttpServletRequest request) {
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
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
        return "redirect:/auth/confirmRegister";
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

    @GetMapping("/confirmRegister")
    String confirmRegisterPage(){
        return "confirmRegister";
    }

    @GetMapping("/confirmRegister/{token}")
    String confirmRegisterTokenPage(@PathVariable String token,Model model){
        model.addAttribute("token",token);
        return "confirmRegisterToken";
    }

    @PostMapping("/confirmRegister/{token}")
    String confirmRegisterToken(@PathVariable String token){
        Optional<VerificationToken> optionalVerificationToken = verificationTokenService.findByToken(token);
        if (optionalVerificationToken.isEmpty()){
            return "redirect:/auth/confirmRegister?errorNoFound";
        }
        VerificationToken verificationToken = optionalVerificationToken.get();
        if (verificationTokenService.isExpired(verificationToken)){
            return "redirect:/auth/confirmRegister?errorExp";
        }
        if(!userService.isUserNotVerified(verificationToken.getUser())){
            return "redirect:/auth/confirmRegister?errorVerify";
        }
        verificationTokenService.verifyByToken(verificationToken);
        return "redirect:/auth/signin";
    }


    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
