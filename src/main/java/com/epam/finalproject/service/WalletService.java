package com.epam.finalproject.service;

import com.epam.finalproject.dto.WalletDTO;
import com.epam.finalproject.payload.request.AddMoneyRequest;
import com.epam.finalproject.payload.request.CreateWalletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletService {

    List<WalletDTO> findAllByUsername(String username);

    Page<WalletDTO> findAllByUsername(Pageable pageable, String username);

    WalletDTO findByNameAndUsername(String name, String username);

    Page<WalletDTO> findAll(Pageable pageable);

    WalletDTO findById(Long id);

    WalletDTO addMoney(AddMoneyRequest request);

    WalletDTO create(CreateWalletRequest request,String username);
}
