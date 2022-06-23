package com.epam.finalproject.util;

import com.epam.finalproject.entity.ReceiptStatusEnum;
import com.epam.finalproject.search.ReceiptSearch;
import com.epam.finalproject.search.ReceiptSearchDTO;
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
public class SearchDTOResolver {

    Map<String, Sort> receiptSort;

    Map<String, ReceiptStatusEnum> receiptStatus;

    public ReceiptSearch resolve(ReceiptSearchDTO receiptSearchDTO) {
        int pageValue = Optional.ofNullable(receiptSearchDTO.getPage()).orElse(0);

        int countValue = Optional.ofNullable(receiptSearchDTO.getCount()).orElse(10);

        String sortValue = Optional.ofNullable(receiptSearchDTO.getSort()).orElse("");

        Sort sort = receiptSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        Set<ReceiptStatusEnum> receiptStatuses = Optional.ofNullable(receiptSearchDTO.getStatus())
                .orElse(Set.of())
                .stream()
                .map(e -> Optional.ofNullable(receiptStatus.get(e)).orElseThrow())
                .collect(Collectors.toSet());

        return ReceiptSearch.builder()
                .receiptStatuses(receiptStatuses)
                .userUsername(receiptSearchDTO.getUser())
                .masterUsername(receiptSearchDTO.getMaster())
                .pageRequest(PageRequest.of(pageValue, countValue, sort))
                .build();
    }
}
