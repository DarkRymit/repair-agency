package com.epam.finalproject.service.impl;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.repository.ReceiptRepository;
import com.epam.finalproject.service.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    ReceiptRepository receiptRepository;

    @Override
    public List<Receipt> findAll() {
        return receiptRepository.findAll(Sort.by("creationTime"));
    }

    @Override
    public Page<Receipt> findAllPage(Pageable pageable) {
        return receiptRepository.findAll(pageable);
    }

    @Override
    public Page<Receipt> findAllPage(int page, int size) {
        return receiptRepository.findAll(PageRequest.of(page,size,Sort.by("creationTime")));
    }

    @Override
    public Page<Receipt> findAllPage(int page, int size, String sort) {
        return receiptRepository.findAll(PageRequest.of(page,size,Sort.by(sort)));
    }

    @Override
    public Page<Receipt> findAllPage(int page, int size, Sort sort) {
        return receiptRepository.findAll(PageRequest.of(page,size,sort));
    }
}
