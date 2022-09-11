package com.epam.finalproject.controller;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.ReceiptResponseDTO;
import com.epam.finalproject.dto.ReceiptStatusFlowDTO;
import com.epam.finalproject.payload.request.search.ReceiptWithMasterSearchRequest;
import com.epam.finalproject.service.ReceiptResponseService;
import com.epam.finalproject.service.ReceiptStatusFlowService;
import com.epam.finalproject.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/master")
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasRole('MASTER') || hasRole('ADMIN')")
public class MasterController {

    public static final String ACTIVE = "active";

    public static final String MASTER_VIEW = "cabinet";
    public static final String TYPE = "type";
    public static final String MASTER = "master";

    SearchService searchService;

    ReceiptResponseService receiptResponseService;

    ReceiptStatusFlowService receiptStatusFlowService;


    @GetMapping("/orders")
    String ordersPage(Model model,@AuthenticationPrincipal UserDetails userDetails, @Valid
    ReceiptWithMasterSearchRequest searchRequest) {
        Page<ReceiptDTO> receipts = searchService.findBySearch(searchRequest, userDetails.getUsername());
        List<ReceiptStatusFlowDTO> flows = receiptStatusFlowService.listAllAvailableForUser(userDetails.getUsername());
        model.addAttribute("flows",flows);
        model.addAttribute("search", searchRequest);
        model.addAttribute("receipts", receipts);
        model.addAttribute(ACTIVE, "orders");
        model.addAttribute(TYPE, MASTER);
        return MASTER_VIEW;
    }

    @GetMapping("/responses")
    String responsesPage(Model model,@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "page",required = false) Integer page) {
        int actualPage = Optional.ofNullable(page).orElse(0);
        Page<ReceiptResponseDTO> responses = receiptResponseService.findByMasterUsername(userDetails.getUsername(), PageRequest.of(actualPage, 5));
        model.addAttribute("responses", responses);
        model.addAttribute(ACTIVE, "responses");
        model.addAttribute(TYPE, MASTER);
        return MASTER_VIEW;
    }
}
