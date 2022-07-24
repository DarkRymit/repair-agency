package com.epam.finalproject.controller;

import com.epam.finalproject.dto.AppCurrencyDTO;
import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.dto.RepairWorkDTO;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import com.epam.finalproject.service.AppCurrencyService;
import com.epam.finalproject.service.ReceiptService;
import com.epam.finalproject.service.RepairCategoryService;
import com.epam.finalproject.service.RepairWorkService;
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

    RepairCategoryService repairCategoryService;

    RepairWorkService repairWorkService;

    AppCurrencyService appCurrencyService;

    @PatchMapping("/{id}")
    String update(Model model, @RequestBody ReceiptUpdateRequest updateRequest, @RequestParam(required = false) String redirectURL, @PathVariable Long id) {
        updateRequest.setId(id);
        ReceiptDTO receipt = receiptService.update(updateRequest);
        return "redirect:/"+receipt.getId();
    }
    @PostMapping(value = "/{id}/status/change",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String updateStatus(Model model,@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id,Long statusId) {
        ReceiptDTO receipt = receiptService.updateStatus(id,statusId,userDetails.getUsername());
        return "redirect:/order/"+receipt.getId();
    }
    @GetMapping("/{id}")
    String show(Model model, @RequestParam(required = false) String redirectURL, @PathVariable Long id) {
        ReceiptDTO receipt = receiptService.findById(id);
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
