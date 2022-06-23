package com.epam.finalproject.controller;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.search.ReceiptSearchDTO;
import com.epam.finalproject.service.ReceiptService;
import com.epam.finalproject.service.SearchService;
import com.epam.finalproject.service.UserService;
import com.epam.finalproject.util.SearchDTOResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
@AllArgsConstructor
@Slf4j
public class ManagerController {

    public static final String ACTIVE = "active";

    public static final String MANAGER_VIEW = "manager";

    SearchService searchService;

    ReceiptService receiptService;

    UserService userService;

    SearchDTOResolver searchDTOResolver;

    @GetMapping("/home")
    String homePage(Model model) {
        model.addAttribute(ACTIVE, "home");
        return MANAGER_VIEW;
    }

    @GetMapping("/orders")
    String ordersPage(Model model, ReceiptSearchDTO receiptSearchDTO) {
        Page<Receipt> receipts = searchService.findBySearch(searchDTOResolver.resolve(receiptSearchDTO));
        model.addAttribute("search",receiptSearchDTO);
        model.addAttribute("receipts", receipts);
        model.addAttribute(ACTIVE, "orders");
        return MANAGER_VIEW;
    }

    @GetMapping("/users")
    String usersPage(Model model) {
        model.addAttribute(ACTIVE, "users");
        return MANAGER_VIEW;
    }

    @GetMapping("/masters")
    String mastersPage(Model model) {
        model.addAttribute(ACTIVE, "masters");
        return MANAGER_VIEW;
    }
}
