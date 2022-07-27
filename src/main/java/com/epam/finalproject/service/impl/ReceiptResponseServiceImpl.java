package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.ReceiptResponseDTO;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptResponse;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.payload.request.ReceiptResponseCreateRequest;
import com.epam.finalproject.repository.ReceiptRepository;
import com.epam.finalproject.repository.ReceiptResponseRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.service.ReceiptResponseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReceiptResponseServiceImpl implements ReceiptResponseService {

    ReceiptRepository receiptRepository;

    ReceiptResponseRepository receiptResponseRepository;

    UserRepository userRepository;

    ModelMapper modelMapper;


    @Override
    public List<ReceiptResponseDTO> findAll() {
        return receiptResponseRepository.findAll().stream().map(this::constructDTO).collect(Collectors.toList());
    }

    @Override
    public ReceiptResponseDTO createNew(ReceiptResponseCreateRequest createRequest, String username) {
        ReceiptResponse result;

        if (receiptResponseRepository.existsByReceipt_Id(createRequest.getReceiptId())){
            throw new IllegalArgumentException("Not allowed caused by response already exist");
        }

        Receipt receipt = receiptRepository.findById(createRequest.getReceiptId()).orElseThrow();

        if(!receipt.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("Not allowed caused by username not match");
        }
        if(!receipt.getStatus().getName().equals(ReceiptStatusEnum.DONE)){
            throw new IllegalStateException("Not allowed caused by receipt not done");
        }

        result = ReceiptResponse.builder()
                .receipt(receipt)
                .text(createRequest.getText())
                .rating(createRequest.getRating())
                .build();
        result = receiptResponseRepository.save(result);
        return constructDTO(result);
    }

    @Override
    public ReceiptResponseDTO constructDTO(ReceiptResponse receiptResponse) {
        return modelMapper.map(receiptResponse,ReceiptResponseDTO.class);
    }

    @Override
    public ReceiptResponseDTO findById(Long id) {
        return constructDTO(receiptResponseRepository.findById(id).orElseThrow());
    }

    @Override
    public ReceiptResponseDTO findByReceiptId(Long id) {
        return constructDTO(receiptResponseRepository.findByReceipt_Id(id).orElseThrow());
    }

    @Override
    public boolean existByReceiptId(Long id) {
        return receiptResponseRepository.existsByReceipt_Id(id);
    }
}
