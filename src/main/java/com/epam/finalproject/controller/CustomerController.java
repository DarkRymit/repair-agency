package com.epam.finalproject.controller;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.ReceiptStatusFlowDTO;
import com.epam.finalproject.payload.request.search.ReceiptWithCustomerSearchRequest;
import com.epam.finalproject.service.ReceiptService;
import com.epam.finalproject.service.ReceiptStatusFlowService;
import com.epam.finalproject.service.SearchService;
import com.epam.finalproject.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
@AllArgsConstructor
@Slf4j
public class CustomerController {

    public static final String ACTIVE = "active";

    public static final String MASTER_VIEW = "cabinet";

    public static final String TYPE = "type";
    public static final String CUSTOMER = "customer";

    SearchService searchService;

    ReceiptService receiptService;

    UserService userService;

    ReceiptStatusFlowService receiptStatusFlowService;

    @GetMapping("/home")
    String homePage(Model model) {
        model.addAttribute(ACTIVE, "home");
        model.addAttribute(TYPE, CUSTOMER);
        return MASTER_VIEW;
    }

    @GetMapping("/orders")
    String ordersPage(Model model, @AuthenticationPrincipal UserDetails userDetails, ReceiptWithCustomerSearchRequest searchRequest) {
        Page<ReceiptDTO> receipts = searchService.findBySearch(searchRequest,userDetails.getUsername());
        List<ReceiptStatusFlowDTO> flows = receiptStatusFlowService.listAllAvailableForUser(userDetails.getUsername());
        model.addAttribute("flows",flows);
        model.addAttribute("search",searchRequest);
        model.addAttribute("receipts", receipts);
        model.addAttribute(ACTIVE, "orders");
        model.addAttribute(TYPE, CUSTOMER);
        return MASTER_VIEW;
    }

    @GetMapping("/responses")
    String responsesPage(Model model) {
        model.addAttribute(ACTIVE, "responses");
        model.addAttribute(TYPE, CUSTOMER);
        return MASTER_VIEW;
    }
}
