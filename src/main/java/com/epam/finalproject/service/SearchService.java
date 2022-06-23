package com.epam.finalproject.service;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.search.ReceiptSearch;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<Receipt> findBySearch(ReceiptSearch receiptSearch);
}
