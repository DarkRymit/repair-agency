package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.search.*;
import com.epam.finalproject.payload.request.search.*;
import com.epam.finalproject.repository.ReceiptRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.specification.ReceiptSpecifications;
import com.epam.finalproject.repository.specification.UserSpecifications;
import com.epam.finalproject.service.SearchService;
import com.epam.finalproject.util.SearchRequestResolver;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService {

    ReceiptRepository receiptRepository;

    UserRepository userRepository;

    SearchRequestResolver searchRequestResolver;

    ModelMapper modelMapper;

    @Override
    @Loggable
    public Page<ReceiptDTO> findBySearch(ReceiptSearch receiptSearch) {
        return receiptRepository.findAll(ReceiptSpecifications.matchSearch(receiptSearch), receiptSearch.getPageRequest())
                .map(receipt -> modelMapper.map(receipt, ReceiptDTO.class));

    }

    @Override
    @Loggable
    public Page<ReceiptDTO> findBySearch(ReceiptSearchRequest receiptSearchRequest) {
        return findBySearch(searchRequestResolver.resolve(receiptSearchRequest));
    }

    @Override
    @Loggable
    public Page<ReceiptDTO> findBySearch(ReceiptWithCustomerSearch receiptSearch) {
        return receiptRepository.findAll(ReceiptSpecifications.matchSearch(receiptSearch), receiptSearch.getPageRequest())
                .map(receipt -> modelMapper.map(receipt, ReceiptDTO.class));
    }

    @Override
    @Loggable
    public Page<ReceiptDTO> findBySearch(ReceiptWithCustomerSearchRequest receiptSearchRequest, String customer) {
        User user = userRepository.findByUsername(customer).orElseThrow();
        return findBySearch(searchRequestResolver.resolve(receiptSearchRequest, user));
    }

    @Override
    @Loggable
    public Page<ReceiptDTO> findBySearch(ReceiptWithMasterSearch receiptSearch) {
        return receiptRepository.findAll(ReceiptSpecifications.matchSearch(receiptSearch), receiptSearch.getPageRequest())
                .map(receipt -> modelMapper.map(receipt, ReceiptDTO.class));
    }

    @Override
    @Loggable
    public Page<ReceiptDTO> findBySearch(ReceiptWithMasterSearchRequest receiptSearchRequest, String master) {
        User user = userRepository.findByUsername(master).orElseThrow();
        return findBySearch(searchRequestResolver.resolve(receiptSearchRequest, user));
    }

    @Override
    @Loggable
    public Page<UserDTO> findBySearch(UserSearch userSearch) {
        return userRepository.findAll(UserSpecifications.matchSearch(userSearch), userSearch.getPageRequest())
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    @Loggable
    public Page<UserDTO> findBySearch(UserSearchRequest userSearchRequest) {
        return findBySearch(searchRequestResolver.resolve(userSearchRequest));
    }

    @Override
    @Loggable
    public Page<UserDTO> findBySearch(MasterSearch masterSearch) {
        return userRepository.findAll(UserSpecifications.matchSearch(masterSearch), masterSearch.getPageRequest())
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    @Loggable
    public Page<UserDTO> findBySearch(MasterSearchRequest masterSearchRequest) {
        return findBySearch(searchRequestResolver.resolve(masterSearchRequest));
    }
}
