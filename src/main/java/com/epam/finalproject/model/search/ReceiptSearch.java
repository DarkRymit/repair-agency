package com.epam.finalproject.model.search;

import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

@Value
@AllArgsConstructor
@Builder
public class ReceiptSearch {
    PageRequest pageRequest;
    Set<ReceiptStatusEnum> receiptStatuses;
    String masterUsername;
    String customerUsername;
}
