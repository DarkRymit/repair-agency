package com.epam.finalproject.service;

import com.epam.finalproject.entity.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ReceiptService {
    List<Receipt> findAll();

    Page<Receipt> findAllPage(Pageable pageable);

    Page<Receipt> findAllPage(int page, int size);

    Page<Receipt> findAllPage(int page, int size, String sort);

    Page<Receipt> findAllPage(int page, int size, Sort sort);
}
