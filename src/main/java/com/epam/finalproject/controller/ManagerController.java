package com.epam.finalproject.controller;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.ReceiptResponseDTO;
import com.epam.finalproject.dto.ReceiptStatusFlowDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.payload.request.search.MasterSearchRequest;
import com.epam.finalproject.payload.request.search.ReceiptSearchRequest;
import com.epam.finalproject.payload.request.search.UserSearchRequest;
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
@RequestMapping("/manager")
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasRole('MANAGER') || hasRole('ADMIN')")
public class ManagerController {

    public static final String ACTIVE = "active";

    public static final String MANAGER_VIEW = "cabinet";

    public static final String TYPE = "type";
    public static final String MANAGER = "manager";
    public static final String SEARCH = "search";

    SearchService searchService;
    ReceiptResponseService receiptResponseService;
    ReceiptStatusFlowService receiptStatusFlowService;

    @GetMapping("/orders")
    String ordersPage(Model model,@AuthenticationPrincipal UserDetails userDetails, @Valid
    ReceiptSearchRequest receiptSearchRequest) {
        Page<ReceiptDTO> receipts = searchService.findBySearch(receiptSearchRequest);
        List<ReceiptStatusFlowDTO> flows = receiptStatusFlowService.listAllAvailableForUser(userDetails.getUsername());
        model.addAttribute("flows",flows);
        model.addAttribute(SEARCH,receiptSearchRequest);
        model.addAttribute("receipts", receipts);
        model.addAttribute(ACTIVE, "orders");
        model.addAttribute(TYPE, MANAGER);
        return MANAGER_VIEW;
    }

    @GetMapping("/users")
    String usersPage(Model model,@Valid  UserSearchRequest userSearchRequest) {
        Page<UserDTO> users = searchService.findBySearch(userSearchRequest);
        model.addAttribute(SEARCH,userSearchRequest);
        model.addAttribute("users", users);
        model.addAttribute(ACTIVE, "users");
        model.addAttribute(TYPE, MANAGER);
        return MANAGER_VIEW;
    }

    @GetMapping("/masters")
    String mastersPage(Model model, @Valid  MasterSearchRequest masterSearchRequest) {
        Page<UserDTO> masters = searchService.findBySearch(masterSearchRequest);
        model.addAttribute(SEARCH,masterSearchRequest);
        model.addAttribute("masters", masters);
        model.addAttribute(ACTIVE, "masters");
        model.addAttribute(TYPE, MANAGER);
        return MANAGER_VIEW;
    }
    @GetMapping("/responses")
    String responsesPage(Model model,@RequestParam(value = "page",required = false) Integer page) {
        int actualPage = Optional.ofNullable(page).orElse(0);
        Page<ReceiptResponseDTO> responses = receiptResponseService.findAll(PageRequest.of(actualPage,5));
        model.addAttribute("responses", responses);
        model.addAttribute(ACTIVE, "responses");
        model.addAttribute(TYPE, MANAGER);
        return MANAGER_VIEW;
    }
}
