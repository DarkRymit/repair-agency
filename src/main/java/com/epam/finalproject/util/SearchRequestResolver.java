package com.epam.finalproject.util;

import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.MasterSearch;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.UserSearch;
import com.epam.finalproject.payload.request.search.MasterSearchRequest;
import com.epam.finalproject.payload.request.search.ReceiptSearchRequest;
import com.epam.finalproject.payload.request.search.UserSearchRequest;
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

        String sortValue = getValueOrDefault(receiptSearchRequest.getSort(),"");

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
                .pageRequest(PageRequest.of(getValueOrZero(receiptSearchRequest.getPage()), getValueOrDefault(receiptSearchRequest.getCount(), 10), sort))
                .build();
    }

    public UserSearch resolve(UserSearchRequest userSearchRequest) {

        String sortValue = Optional.ofNullable(userSearchRequest.getSort()).orElse("");

        Sort sort = userSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        return UserSearch.builder()
                .username(userSearchRequest.getUsername())
                .pageRequest(PageRequest.of(getValueOrZero(userSearchRequest.getPage()), getValueOrDefault(userSearchRequest.getCount(), 10), sort))
                .build();
    }

    public MasterSearch resolve(MasterSearchRequest masterSearchRequest) {

        String sortValue = getValueOrDefault(masterSearchRequest.getSort(),"");

        Sort sort = userSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        return MasterSearch.builder()
                .username(masterSearchRequest.getUsername())
                .pageRequest(PageRequest.of(getValueOrZero(masterSearchRequest.getPage()), getValueOrDefault(masterSearchRequest.getCount(), 10), sort))
                .build();
    }

    private Integer getValueOrZero(Integer value) {
        return getValueOrDefault(value, 0);
    }

    private <T> T getValueOrDefault(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
}
