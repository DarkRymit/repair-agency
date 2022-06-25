package com.epam.finalproject.util;

import com.epam.finalproject.entity.ReceiptStatusEnum;
import com.epam.finalproject.search.ReceiptSearch;
import com.epam.finalproject.search.ReceiptSearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SearchRequestResolver {

    Map<String, Sort> receiptSort;

    Map<String, ReceiptStatusEnum> receiptStatus;

    public ReceiptSearch resolve(ReceiptSearchRequest receiptSearchRequest) {
        int pageValue = Optional.ofNullable(receiptSearchRequest.getPage()).orElse(0);

        int countValue = Optional.ofNullable(receiptSearchRequest.getCount()).orElse(10);

        String sortValue = Optional.ofNullable(receiptSearchRequest.getSort()).orElse("");

        Sort sort = receiptSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        Set<ReceiptStatusEnum> receiptStatuses = Optional.ofNullable(receiptSearchRequest.getStatus())
                .orElse(Set.of())
                .stream()
                .map(e -> Optional.ofNullable(receiptStatus.get(e)).orElseThrow())
                .collect(Collectors.toSet());

        return ReceiptSearch.builder()
                .receiptStatuses(receiptStatuses)
                .userUsername(receiptSearchRequest.getUser())
                .masterUsername(receiptSearchRequest.getMaster())
                .pageRequest(PageRequest.of(pageValue, countValue, sort))
                .build();
    }
}
