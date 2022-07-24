package com.epam.finalproject.service;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.model.search.*;
import com.epam.finalproject.payload.request.search.*;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<ReceiptDTO> findBySearch(ReceiptSearch receiptSearch);
    Page<ReceiptDTO> findBySearch(ReceiptSearchRequest receiptSearchRequest);
    Page<ReceiptDTO> findBySearch(ReceiptWithCustomerSearch searchRequest);
    Page<ReceiptDTO> findBySearch(ReceiptWithCustomerSearchRequest searchRequest,String customer);
    Page<ReceiptDTO> findBySearch(ReceiptWithMasterSearch searchRequest);
    Page<ReceiptDTO> findBySearch(ReceiptWithMasterSearchRequest searchRequest,String master);

    Page<UserDTO> findBySearch(UserSearch userSearch);
    Page<UserDTO> findBySearch(UserSearchRequest userSearchRequest);

    Page<UserDTO> findBySearch(MasterSearch masterSearch);
    Page<UserDTO> findBySearch(MasterSearchRequest masterSearchRequest);
}
