package com.epam.finalproject.controller;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.payload.request.search.ReceiptSearchRequest;
import com.epam.finalproject.payload.request.search.UserSearchRequest;
import com.epam.finalproject.service.ReceiptService;
import com.epam.finalproject.service.SearchService;
import com.epam.finalproject.service.UserService;
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


    @GetMapping("/home")
    String homePage(Model model) {
        model.addAttribute(ACTIVE, "home");
        return MANAGER_VIEW;
    }

    @GetMapping("/orders")
    String ordersPage(Model model, ReceiptSearchRequest receiptSearchRequest) {
        Page<ReceiptDTO> receipts = searchService.findBySearch(receiptSearchRequest).map(r -> receiptService.constructDTO(r));
        model.addAttribute("search",receiptSearchRequest);
        model.addAttribute("receipts", receipts);
        model.addAttribute(ACTIVE, "orders");
        return MANAGER_VIEW;
    }

    @GetMapping("/users")
    String usersPage(Model model, UserSearchRequest userSearchRequest) {
        Page<User> users = searchService.findBySearch(userSearchRequest);
        model.addAttribute("search",userSearchRequest);
        model.addAttribute("users", users);
        model.addAttribute(ACTIVE, "users");
        return "manager-users";
    }

    @GetMapping("/masters")
    String mastersPage(Model model) {
        model.addAttribute(ACTIVE, "masters");
        return MANAGER_VIEW;
    }
    @GetMapping("/responses")
    String responsesPage(Model model) {
        model.addAttribute(ACTIVE, "responses");
        return MANAGER_VIEW;
    }
}
