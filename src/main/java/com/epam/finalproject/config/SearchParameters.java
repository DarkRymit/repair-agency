package com.epam.finalproject.config;

import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.domain.Sort;

import java.util.Map;

@Value
@Getter
public class SearchParameters {

    Map<String, Sort> receiptSort;

    Map<String, Sort> userSort;

    Map<String, ReceiptStatusEnum> receiptStatus;

    public SearchParameters(Map<String, Sort> receiptSort, Map<String, Sort> userSort,
            Map<String, ReceiptStatusEnum> receiptStatus) {
        this.receiptSort = receiptSort;
        this.userSort = userSort;
        this.receiptStatus = receiptStatus;
    }
}
