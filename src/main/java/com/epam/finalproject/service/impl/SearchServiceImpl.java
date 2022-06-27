package com.epam.finalproject.service.impl;

import com.epam.finalproject.entity.Receipt;
import com.epam.finalproject.entity.User;
import com.epam.finalproject.repository.ReceiptRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.specification.ReceiptSpecifications;
import com.epam.finalproject.repository.specification.UserSpecifications;
import com.epam.finalproject.search.ReceiptSearch;
import com.epam.finalproject.search.ReceiptSearchRequest;
import com.epam.finalproject.search.UserSearch;
import com.epam.finalproject.search.UserSearchRequest;
import com.epam.finalproject.service.SearchService;
import com.epam.finalproject.util.SearchRequestResolver;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService {

    ReceiptRepository receiptRepository;

    UserRepository userRepository;

    SearchRequestResolver searchRequestResolver;

    @Override
    public Page<Receipt> findBySearch(ReceiptSearch receiptSearch) {
        return receiptRepository.findAll(ReceiptSpecifications.matchSearch(receiptSearch), receiptSearch.getPageRequest());

    }

    @Override
    public Page<Receipt> findBySearch(ReceiptSearchRequest receiptSearchRequest) {
        return findBySearch(searchRequestResolver.resolve(receiptSearchRequest));
    }

    @Override
    public Page<User> findBySearch(UserSearch userSearch) {
        return userRepository.findAll(UserSpecifications.matchSearch(userSearch), userSearch.getPageRequest());
    }

    @Override
    public Page<User> findBySearch(UserSearchRequest userSearchRequest) {
        return findBySearch(searchRequestResolver.resolve(userSearchRequest));
    }
}
