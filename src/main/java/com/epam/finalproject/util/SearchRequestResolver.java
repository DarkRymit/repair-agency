package com.epam.finalproject.util;

import com.epam.finalproject.entity.ReceiptStatusEnum;
import com.epam.finalproject.search.ReceiptSearch;
import com.epam.finalproject.search.ReceiptSearchRequest;
import com.epam.finalproject.search.UserSearch;
import com.epam.finalproject.search.UserSearchRequest;
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

    Map<String, Sort> userSort;

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
    public UserSearch resolve(UserSearchRequest userSearchRequest) {
        int pageValue = Optional.ofNullable(userSearchRequest.getPage()).orElse(0);

        int countValue = Optional.ofNullable(userSearchRequest.getCount()).orElse(10);

        String sortValue = Optional.ofNullable(userSearchRequest.getSort()).orElse("");

        Sort sort = userSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        return UserSearch.builder()
                .username(userSearchRequest.getUsername())
                .pageRequest(PageRequest.of(pageValue, countValue, sort))
                .build();
    }
}
