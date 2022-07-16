package com.epam.finalproject.service;

import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.search.*;
import com.epam.finalproject.payload.request.search.*;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<Receipt> findBySearch(ReceiptSearch receiptSearch);
    Page<Receipt> findBySearch(ReceiptSearchRequest receiptSearchRequest);
    Page<Receipt> findBySearch(ReceiptWithCustomerSearch searchRequest);
    Page<Receipt> findBySearch(ReceiptWithCustomerSearchRequest searchRequest,User customer);
    Page<Receipt> findBySearch(ReceiptWithMasterSearch searchRequest);
    Page<Receipt> findBySearch(ReceiptWithMasterSearchRequest searchRequest,User master);

    Page<User> findBySearch(UserSearch userSearch);
    Page<User> findBySearch(UserSearchRequest userSearchRequest);

    Page<User> findBySearch(MasterSearch masterSearch);
    Page<User> findBySearch(MasterSearchRequest masterSearchRequest);
}
