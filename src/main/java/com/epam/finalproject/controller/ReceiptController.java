package com.epam.finalproject.controller;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import com.epam.finalproject.service.ReceiptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class ReceiptController {
    ReceiptService receiptService;

    @PatchMapping("/{id}")
    String update(Model model, @RequestBody ReceiptUpdateRequest updateRequest, @RequestParam(required = false) String redirectURL, @PathVariable Long id) {
        updateRequest.setId(id);
        Receipt receipt = receiptService.update(updateRequest);
        return "redirect:/"+receipt.getId();
    }

}
