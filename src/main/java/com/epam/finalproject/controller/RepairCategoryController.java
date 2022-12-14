package com.epam.finalproject.controller;

import com.epam.finalproject.currency.context.CurrencyUnitContextHolder;
import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.service.AppCurrencyService;
import com.epam.finalproject.service.RepairCategoryService;
import com.epam.finalproject.service.RepairWorkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
@AllArgsConstructor
@Slf4j
public class RepairCategoryController {

    RepairCategoryService repairCategoryService;

    RepairWorkService repairWorkService;

    AppCurrencyService appCurrencyService;

    @GetMapping("")
    String allCategoryPage(Model model) {
        List<RepairCategoryDTO> categories = repairCategoryService.findAll();
        AppCurrency currency = appCurrencyService.findByCode(CurrencyUnitContextHolder.getCurrencyUnit().getCurrencyCode());
        model.addAttribute("categories",categories);
        model.addAttribute("currency",currency);
        return "categories";
    }

    @GetMapping("/{keyName}")
    String allCategoryPage(Model model,@PathVariable String keyName) {
        RepairCategoryDTO category = repairCategoryService.findByKeyName(keyName);
        AppCurrency currency = appCurrencyService.findByCode(CurrencyUnitContextHolder.getCurrencyUnit().getCurrencyCode());
        model.addAttribute("category",category);
        model.addAttribute("currency",currency);
        return "category";
    }


}
