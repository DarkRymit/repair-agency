package com.epam.finalproject.service;

import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.search.MasterSearch;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.UserSearch;
import com.epam.finalproject.payload.request.search.MasterSearchRequest;
import com.epam.finalproject.payload.request.search.ReceiptSearchRequest;
import com.epam.finalproject.payload.request.search.UserSearchRequest;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<Receipt> findBySearch(ReceiptSearch receiptSearch);
    Page<Receipt> findBySearch(ReceiptSearchRequest receiptSearchRequest);
    Page<User> findBySearch(UserSearch userSearch);
    Page<User> findBySearch(UserSearchRequest userSearchRequest);
    Page<User> findBySearch(MasterSearch masterSearch);
    Page<User> findBySearch(MasterSearchRequest masterSearchRequest);
}
