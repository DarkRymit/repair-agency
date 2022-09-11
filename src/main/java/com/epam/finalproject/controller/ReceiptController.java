package com.epam.finalproject.controller;

import com.epam.finalproject.currency.context.CurrencyUnitContextHolder;
import com.epam.finalproject.dto.*;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.payload.request.ReceiptResponseCreateRequest;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.pay.ReceiptPayRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import com.epam.finalproject.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class ReceiptController {
    ReceiptService receiptService;

    ReceiptStatusFlowService receiptStatusFlowService;

    ReceiptResponseService receiptResponseService;

    RepairCategoryService repairCategoryService;

    RepairWorkService repairWorkService;

    WalletService walletService;
    AppCurrencyService appCurrencyService;


    @GetMapping("/{id}/update")
    @PreAuthorize("hasRole('MANAGER') || hasRole('ADMIN')")
    String updatePage(Model model,@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id) {
        ReceiptDTO receipt = receiptService.findById(id);
        List<ReceiptStatusFlowDTO> flows = receiptStatusFlowService.listAllAvailableForUser(receipt.getStatus().getId(),
                userDetails.getUsername());
        List<AppCurrency> currencies = appCurrencyService.findAll();
        model.addAttribute("order", receipt);
        model.addAttribute("flows", flows);
        model.addAttribute("currencies", currencies);
        return "orderUpdate";
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasRole('MANAGER') || hasRole('ADMIN')")
    String update(@Valid @RequestBody ReceiptUpdateRequest updateRequest, @PathVariable("id") Long id) {
        updateRequest.setId(id);
        ReceiptDTO receipt = receiptService.update(updateRequest);
        return "redirect:/order/" + receipt.getId();
    }

    @PostMapping(value = "/{id}/response/create",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    String responseCreate(@Valid ReceiptResponseCreateRequest createRequest,@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("id") Long id) {
        createRequest.setReceiptId(id);
        receiptResponseService.createNew(createRequest, userDetails.getUsername());
        return "redirect:/order/" + id;
    }

    @PostMapping("/{id}/status/change")
    @PreAuthorize("isAuthenticated()")
    String updateStatus(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id, @RequestParam("statusId") Long statusId) {
        ReceiptDTO receipt = receiptService.updateStatus(id, statusId, userDetails.getUsername());
        return "redirect:/order/" + receipt.getId();
    }

    @GetMapping(value = "/{id}/pay")
    @PreAuthorize("hasRole('CUSTOMER')")
    String pay(Model model,@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id) {
        ReceiptDTO receipt = receiptService.findById(id);
        List<WalletDTO> wallets = walletService.findAllByUsername(userDetails.getUsername());
        model.addAttribute("order", receipt);
        model.addAttribute("wallets", wallets);
        return "orderPay";
    }

    @PostMapping(value = "/{id}/pay",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    String pay(@AuthenticationPrincipal UserDetails userDetails, @Valid ReceiptPayRequest payRequest,
            @PathVariable("id") Long id) {
        payRequest.setId(id);
        ReceiptDTO receipt = receiptService.pay(payRequest, userDetails.getUsername());
        return "redirect:/order/" + receipt.getId();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('MASTER') || hasRole('MANAGER') || hasRole('ADMIN') ||" +
            " (hasRole('CUSTOMER') && #model[order].user.username == authentication.principal.username)")
    String show(Model model, @PathVariable("id") Long id) {
        ReceiptDTO receipt = receiptService.findById(id);
        if (receiptResponseService.existByReceiptId(id)) {
            ReceiptResponseDTO response = receiptResponseService.findByReceiptId(id);
            log.trace(" Response {}", response);
            model.addAttribute("response", response);
        }
        log.trace("Work {}", receipt);
        model.addAttribute("order", receipt);
        return "order";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    String create(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ReceiptCreateRequest createRequest) {
        ReceiptDTO receipt = receiptService.createNew(createRequest, userDetails.getUsername());
        return "redirect:/order/" + receipt.getId();
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ADMIN')")
    String create(Model model, @RequestParam("category") String category) {
        RepairCategoryDTO repairCategory = repairCategoryService.findByKeyName(category);
        List<RepairWorkDTO> repairWorks = repairWorkService.findByCategoryKey(category);
        AppCurrency appCurrency = appCurrencyService.findByCode(
                CurrencyUnitContextHolder.getCurrencyUnit().getCurrencyCode());
        model.addAttribute("works", repairWorks);
        model.addAttribute("category", repairCategory);
        model.addAttribute("currency", appCurrency);
        return "orderCreate";
    }

}
