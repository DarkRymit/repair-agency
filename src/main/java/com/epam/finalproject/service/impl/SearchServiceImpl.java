package com.epam.finalproject.service.impl;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.repository.ReceiptRepository;
import com.epam.finalproject.repository.specification.ReceiptSpecifications;
import com.epam.finalproject.search.ReceiptSearch;
import com.epam.finalproject.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService {

    ReceiptRepository receiptRepository;

    @Override
    public Page<Receipt> findBySearch(ReceiptSearch receiptSearch) {
        return receiptRepository.findAll(ReceiptSpecifications.matchSearch(receiptSearch), receiptSearch.getPageRequest());

    }
}
