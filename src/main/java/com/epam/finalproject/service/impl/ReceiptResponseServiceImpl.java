package com.epam.finalproject.service.impl;

import com.epam.finalproject.aop.logging.Loggable;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ReceiptResponseServiceImpl implements ReceiptResponseService {

    ReceiptRepository receiptRepository;

    ReceiptResponseRepository receiptResponseRepository;

    UserRepository userRepository;

    ModelMapper modelMapper;



    @Override
    public Page<ReceiptResponseDTO> findAll(Pageable pageable) {
        return  receiptResponseRepository.findAll(pageable).map(this::constructDTO);
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
    public ReceiptResponseDTO findByReceiptId(Long id) {
        return constructDTO(receiptResponseRepository.findByReceipt_Id(id).orElseThrow());
    }

    @Override
    public boolean existByReceiptId(Long id) {
        return receiptResponseRepository.existsByReceipt_Id(id);
    }

    @Override
    @Loggable
    public Page<ReceiptResponseDTO> findByCustomerUsername(String username, Pageable pageable) {
        return receiptResponseRepository.findAllFetchReceiptByReceipt_User_Username(username,pageable).map(this::constructDTO);
    }

    @Override
    public Page<ReceiptResponseDTO> findByMasterUsername(String username, Pageable pageable) {
        return receiptResponseRepository.findAllFetchReceiptByReceipt_Master_Username(username,pageable).map(this::constructDTO);
    }
}
