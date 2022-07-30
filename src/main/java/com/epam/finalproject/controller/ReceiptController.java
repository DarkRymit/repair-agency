package com.epam.finalproject.controller;

import com.epam.finalproject.dto.*;
import com.epam.finalproject.payload.request.ReceiptResponseCreateRequest;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.pay.ReceiptPayRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import com.epam.finalproject.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    String updatePage(Model model,@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        ReceiptDTO receipt = receiptService.findById(id);
        List<ReceiptStatusFlowDTO> flows = receiptStatusFlowService.listAllAvailableForUser(receipt.getStatus().getId(),userDetails.getUsername());
        List<AppCurrencyDTO> currencies = appCurrencyService.findAll();
        model.addAttribute("order",receipt);
        model.addAttribute("flows",flows);
        model.addAttribute("currencies",currencies);
        return "orderUpdate";
    }
    @PostMapping("/{id}/update")
    String update(Model model, @RequestBody ReceiptUpdateRequest updateRequest, @RequestParam(required = false) String redirectURL, @PathVariable Long id) {
        updateRequest.setId(id);
        ReceiptDTO receipt = receiptService.update(updateRequest);
        return "redirect:/order/"+receipt.getId();
    }

    @PostMapping(value ="/{id}/response/create",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String responseCreate(Model model, ReceiptResponseCreateRequest createRequest, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        createRequest.setReceiptId(id);
        log.info(createRequest.toString());
        receiptResponseService.createNew(createRequest,userDetails.getUsername());
        return "redirect:/order/"+id;
    }

    @PostMapping(value = "/{id}/status/change",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String updateStatus(Model model,@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id,Long statusId) {
        ReceiptDTO receipt = receiptService.updateStatus(id,statusId,userDetails.getUsername());
        return "redirect:/order/"+receipt.getId();
    }

    @GetMapping(value ="/{id}/pay")
    String pay(Model model, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        ReceiptDTO receipt = receiptService.findById(id);
        List<WalletDTO> wallets = walletService.findAllByUsername(userDetails.getUsername());
        model.addAttribute("order",receipt);
        model.addAttribute("wallets",wallets);
        return "orderPay";
    }

    @PostMapping(value ="/{id}/pay",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String pay(Model model, @AuthenticationPrincipal UserDetails userDetails, ReceiptPayRequest payRequest, @PathVariable Long id) {
        payRequest.setId(id);
        ReceiptDTO receipt = receiptService.pay(payRequest,userDetails.getUsername());
        return "redirect:/order/"+receipt.getId();
    }

    @GetMapping("/{id}")
    String show(Model model, @RequestParam(required = false) String redirectURL, @PathVariable Long id) {
        ReceiptDTO receipt = receiptService.findById(id);
        if (receiptResponseService.existByReceiptId(id)){
            ReceiptResponseDTO response = receiptResponseService.findByReceiptId(id);
            model.addAttribute("response",response);
        }
        model.addAttribute("order",receipt);
        return "order";
    }

    @PostMapping("/create")
    String create(Model model, @AuthenticationPrincipal UserDetails userDetails, @RequestBody ReceiptCreateRequest createRequest, @RequestParam(required=false) String redirectURL) {
        ReceiptDTO receipt = receiptService.createNew(createRequest,userDetails.getUsername());
        return "redirect:/order/"+receipt.getId();
    }
    @GetMapping("/create")
    String create(Model model, @RequestParam(required=false) String redirectURL, @RequestParam String category, @RequestParam String currency) {
        RepairCategoryDTO repairCategory = repairCategoryService.findByKeyName(category);
        List<RepairWorkDTO> repairWorks = repairWorkService.findByCategoryKey(category);
        AppCurrencyDTO appCurrency = appCurrencyService.findByCode(currency);
        model.addAttribute("works",repairWorks);
        model.addAttribute("category",repairCategory);
        model.addAttribute("currency",appCurrency);
        return "orderCreate";
    }


}
