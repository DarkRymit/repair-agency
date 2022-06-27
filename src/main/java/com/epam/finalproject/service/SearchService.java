package com.epam.finalproject.service;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.search.ReceiptSearch;
import com.epam.finalproject.search.ReceiptSearchRequest;
import com.epam.finalproject.search.UserSearch;
import com.epam.finalproject.search.UserSearchRequest;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<Receipt> findBySearch(ReceiptSearch receiptSearch);
    Page<Receipt> findBySearch(ReceiptSearchRequest receiptSearchRequest);
    Page<User> findBySearch(UserSearch userSearch);
    Page<User> findBySearch(UserSearchRequest userSearchRequest);
}
