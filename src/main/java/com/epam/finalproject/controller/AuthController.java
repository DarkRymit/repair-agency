package com.epam.finalproject.controller;

import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.event.OnPasswordResetEvent;
import com.epam.finalproject.payload.request.NewPasswordRequest;
import com.epam.finalproject.payload.request.PasswordResetRequest;
import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.service.PasswordResetTokenService;
import com.epam.finalproject.service.UserService;
import com.epam.finalproject.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    UserService userService;

    VerificationTokenService verificationTokenService;

    PasswordResetTokenService passwordResetTokenService;

    ApplicationEventPublisher eventPublisher;

    @GetMapping("/signup")
    @PreAuthorize("isAnonymous()")
    String signUpPage() {
        return "signup";
    }


    @PostMapping("/signup")
    @PreAuthorize("isAnonymous()")
    String signUp(@Valid SignUpRequest form, RedirectAttributes redirectedAttributes) {
        boolean isErrorsExists = false;
        if (userService.existsByUsername(form.getUsername())) {
            isErrorsExists = true;
            redirectedAttributes.addFlashAttribute("usernameError", "true");
        }
        if (userService.existsByEmail(form.getEmail())) {
            isErrorsExists = true;
            redirectedAttributes.addFlashAttribute("emailError", "true");
        }
        if (isErrorsExists) {
            return "redirect:/auth/signup";
        }
        User user = userService.signUpNewUserAccount(form);
        log.info("Created user: {}",user);
        return "redirect:/auth/confirmRegister";
    }

    @GetMapping("/signin")
    @PreAuthorize("isAnonymous()")
    String signInPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error!=null)
            model.addAttribute("error","true");
        return "signin";
    }

    @GetMapping("/confirmRegister")
    @PreAuthorize("isAnonymous()")
    String confirmRegisterPage(){
        return "confirmRegister";
    }

    @GetMapping("/confirmRegister/{token}")
    @PreAuthorize("isAnonymous()")
    String confirmRegisterTokenPage(@PathVariable("token") String token, Model model){
        model.addAttribute("token",token);
        return "confirmRegisterToken";
    }

    @PostMapping("/confirmRegister/{token}")
    @PreAuthorize("isAnonymous()")
    String confirmRegisterToken(@PathVariable("token") String token){
        Optional<VerificationToken> optionalVerificationToken = verificationTokenService.findByToken(token);
        if (optionalVerificationToken.isEmpty()){
            return "redirect:/auth/confirmRegister?errorNoFound";
        }
        verificationTokenService.verifyByToken(optionalVerificationToken.orElseThrow());
        return "redirect:/auth/signin";
    }

    @GetMapping("/resetpassword")
    @PreAuthorize("isAnonymous()")
    String resetPasswordPage(){
        return "resetPassword";
    }
    @PostMapping("/resetpassword")
    @PreAuthorize("isAnonymous()")
    String resetPassword(HttpServletRequest request, @Valid PasswordResetRequest resetRequest){
        User user = userService.findByEmail(resetRequest.getEmail()).orElseThrow();
        eventPublisher.publishEvent(new OnPasswordResetEvent(user, request.getLocale()));
        return "redirect:/auth/resetpassword/confirm";
    }

    @GetMapping("/resetpassword/confirm")
    @PreAuthorize("isAnonymous()")
    String resetPasswordConfirmPage(){
        return "resetPasswordConfirm";
    }

    @GetMapping("/resetpassword/confirm/{token}")
    @PreAuthorize("isAnonymous()")
    String resetPasswordConfirmTokenPage(Model model,@PathVariable("token") String token){
        model.addAttribute("token",token);
        return "resetPasswordConfirmToken";
    }
    @PostMapping("/resetpassword/confirm/{token}")
    @PreAuthorize("isAnonymous()")
    String resetPasswordConfirmToken(@PathVariable("token") String token, @Valid NewPasswordRequest newPasswordRequest){
        Optional<PasswordResetToken> optionalPasswordResetToken = passwordResetTokenService.findByToken(token);
        if (optionalPasswordResetToken.isEmpty()){
            return "redirect:/auth/resetpassword/confirm?errorNoFound";
        }
        PasswordResetToken passwordResetToken = optionalPasswordResetToken.get();
        if (passwordResetTokenService.isExpired(passwordResetToken)){
            return "redirect:/auth/resetpassword/confirm?errorExp";
        }
        passwordResetTokenService.newPassword(passwordResetToken,newPasswordRequest);
        return "redirect:/auth/signin";
    }
}
