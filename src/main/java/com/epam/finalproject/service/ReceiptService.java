package com.epam.finalproject.service;

import com.epam.finalproject.entity.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiptService {
    List<Receipt> findAll();

    Page<Receipt> findAll(Pageable pageable);

}
