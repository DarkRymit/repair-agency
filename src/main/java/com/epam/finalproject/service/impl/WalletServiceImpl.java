package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.WalletDTO;
import com.epam.finalproject.model.entity.Wallet;
import com.epam.finalproject.repository.WalletRepository;
import com.epam.finalproject.service.WalletService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    WalletRepository walletRepository;

    ModelMapper modelMapper;


    @Override
    public List<WalletDTO> findAllByUsername(String username) {
        return walletRepository.findAllByUser_Username(username)
                .stream()
                .map(this::constructDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<WalletDTO> findAllByUsername(Pageable pageable, String username) {
        return walletRepository.findAllByUser_Username(pageable, username).map(this::constructDTO);
    }

    @Override
    public WalletDTO findByNameAndUsername(String name, String username) {
        return constructDTO(walletRepository.findDistinctByNameAndUser_Username(name, username).orElseThrow());
    }

    @Override
    public Page<WalletDTO> findAll(Pageable pageable) {
        return walletRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    public WalletDTO findById(Long id) {
        return constructDTO(walletRepository.findById(id).orElseThrow());
    }

    @Loggable
    public WalletDTO constructDTO(Wallet wallet) {
        return modelMapper.map(wallet, WalletDTO.class);
    }

}
