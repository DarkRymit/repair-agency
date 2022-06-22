package com.epam.finalproject.controller;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.service.ReceiptService;
import com.epam.finalproject.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
@AllArgsConstructor
@Slf4j
public class ManagerController {

    public static final String ACTIVE = "active";

    public static final String MANAGER_VIEW = "manager";

    static final Map<String, Sort> receiptSort = Map.of("price", Sort.by("priceAmount"), "create-time", Sort.by("creationTime"), "status", Sort.by("receiptStatus"), "", Sort.by("creationTime"));

    ReceiptService receiptService;

    UserService userService;

    @GetMapping("/home")
    String homePage(Model model) {
        model.addAttribute(ACTIVE,"home");
        return MANAGER_VIEW;
    }

    @GetMapping("/orders")
    String ordersPage(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("count") Optional<Integer> count, @RequestParam("order") Optional<String> order) {
        Page<Receipt> receipts = receiptService.findAll(getPageRequest(page, count, order));
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

    private PageRequest getPageRequest(Optional<Integer> page, Optional<Integer> count, Optional<String> order) {
        int pageValue = page.orElse(0);
        int countValue = count.orElse(10);
        String orderValue = order.orElse("");
        Sort sort = receiptSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(orderValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(receiptSort.get(""));
        return PageRequest.of(pageValue, countValue, sort);
    }
}
