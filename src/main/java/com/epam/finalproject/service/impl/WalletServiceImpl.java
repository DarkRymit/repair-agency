package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
import com.epam.finalproject.dto.WalletDTO;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.Wallet;
import com.epam.finalproject.payload.request.AddMoneyRequest;
import com.epam.finalproject.payload.request.CreateWalletRequest;
import com.epam.finalproject.repository.AppCurrencyRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.repository.WalletRepository;
import com.epam.finalproject.service.WalletService;
import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    WalletRepository walletRepository;

    AppCurrencyRepository appCurrencyRepository;

    UserRepository userRepository;

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
    public WalletDTO addMoney(AddMoneyRequest request) {
        Wallet wallet = walletRepository.findById(request.getId()).orElseThrow();
        Money walletMoney = Money.of(wallet.getMoneyAmount(), wallet.getMoneyCurrency().getCode());
        Money addMoney = Money.of(request.getMoneyToAdd(), wallet.getMoneyCurrency().getCode());
        Money walletRemainder = walletMoney.add(addMoney);
        wallet.setMoneyAmount(walletRemainder.getNumberStripped());
        wallet = walletRepository.save(wallet);
        return constructDTO(wallet);
    }

    @Override
    @Loggable
    public WalletDTO create(CreateWalletRequest request, String username) {
        if (walletRepository.findDistinctByNameAndUser_Username(request.getName().trim(), username).isPresent()) {
            throw new IllegalArgumentException(String.format("Wallet by request exist %s",request));
        }
        AppCurrency currency = appCurrencyRepository.findByCode(request.getCurrency()).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setMoneyCurrency(currency);
        wallet.setMoneyAmount(BigDecimal.ZERO);
        wallet.setName(request.getName().trim());
        walletRepository.save(wallet);
        return constructDTO(wallet);
    }

    @Override
    public Page<WalletDTO> findAll(Pageable pageable) {
        return walletRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    public WalletDTO findById(Long id) {
        return constructDTO(walletRepository.findById(id).orElseThrow());
    }

    public WalletDTO constructDTO(Wallet wallet) {
        return modelMapper.map(wallet, WalletDTO.class);
    }

}
