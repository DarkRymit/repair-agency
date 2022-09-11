package com.epam.finalproject.controller;

import com.epam.finalproject.dto.WalletDTO;
import com.epam.finalproject.payload.request.AddMoneyRequest;
import com.epam.finalproject.payload.request.CreateWalletRequest;
import com.epam.finalproject.service.WalletService;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WalletController.class);
    WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/addMoney")
    @PreAuthorize("hasRole('MANAGER') || hasRole('ADMIN')")
    String addMoney(@Valid  AddMoneyRequest moneyRequest,
            @RequestParam("redirectUrl") String redirectUrl) {
        WalletDTO wallet = walletService.addMoney(moneyRequest);
        log.trace("Wallet updated {}", wallet);
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    String createWallet(@Valid CreateWalletRequest createWalletRequest,
            @RequestParam("redirectUrl") String redirectUrl,
            @AuthenticationPrincipal UserDetails userDetails) {
        WalletDTO wallet = walletService.create(createWalletRequest,userDetails.getUsername());
        log.trace("Wallet created {}", wallet);
        return "redirect:" + redirectUrl;
    }

}
