package com.epam.finalproject.util;

import com.epam.finalproject.config.SearchParameters;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.*;
import com.epam.finalproject.payload.request.search.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Search request resolver.
 * Used as util to convert input from user to search request used inside model
 */
@Component
public class SearchRequestResolver {

    /**
     * The Receipt sort.
     * Map that contains by {@link String} key value of  {@link Sort} for  {@link com.epam.finalproject.model.entity.Receipt}
     */
    Map<String, Sort> receiptSort;

    /**
     * The User sort.
     * Map that contains by {@link String} key value of  {@link Sort} for  {@link User}
     */
    Map<String, Sort> userSort;

    /**
     * The Receipt status.
     * Map that contains by {@link String} key value of  {@link ReceiptStatusEnum}
     */
    Map<String, ReceiptStatusEnum> receiptStatus;

    /**
     * The Search parameters.
     * Object contains all setup for this util component {@link SearchParameters}
     */
    SearchParameters searchParameters;

    /**
     * Instantiates a new Search request resolver.
     *
     * @param searchParameters the search parameters
     */
    public SearchRequestResolver(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
        this.receiptSort = searchParameters.getReceiptSort();
        this.userSort = searchParameters.getUserSort();
        this.receiptStatus = searchParameters.getReceiptStatus();
    }

    /**
     * Resolve receipt search.
     *
     * @param receiptSearchRequest the receipt search request
     * @return the receipt search
     */
    public ReceiptSearch resolve(ReceiptSearchRequest receiptSearchRequest) {

        String sortValue = getValueOrDefault(receiptSearchRequest.getSort(),"");

        Sort sort = getSort(receiptSort, sortValue);

        Set<ReceiptStatusEnum> receiptStatuses = getReceiptStatusEnums(receiptSearchRequest.getStatus());

        return ReceiptSearch.builder()
                .receiptStatuses(receiptStatuses)
                .customerUsername(receiptSearchRequest.getCustomer())
                .masterUsername(receiptSearchRequest.getMaster())
                .pageRequest(PageRequest.of(getValueOrZero(receiptSearchRequest.getPage()), getValueOrDefault(receiptSearchRequest.getCount(), 5), sort))
                .build();
    }

    private Sort getSort(Map<String, Sort> receiptSort, String sortValue) {
        return receiptSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Resolve receipt with customer search.
     *
     * @param receiptSearchRequest the receipt search request
     * @param customer             the customer
     * @return the receipt with customer search
     */
    public ReceiptWithCustomerSearch resolve(ReceiptWithCustomerSearchRequest receiptSearchRequest, User customer) {

        String sortValue = getValueOrDefault(receiptSearchRequest.getSort(),"");

        Sort sort = getSort(receiptSort, sortValue);

        Set<ReceiptStatusEnum> receiptStatuses = getReceiptStatusEnums(receiptSearchRequest.getStatus());

        return ReceiptWithCustomerSearch.builder()
                .receiptStatuses(receiptStatuses)
                .customer(customer)
                .masterUsername(receiptSearchRequest.getMaster())
                .pageRequest(PageRequest.of(getValueOrZero(receiptSearchRequest.getPage()), getValueOrDefault(receiptSearchRequest.getCount(), 5), sort))
                .build();
    }

    private Set<ReceiptStatusEnum> getReceiptStatusEnums(Set<String> receiptSearchRequest) {
        return Optional.ofNullable(receiptSearchRequest)
                .orElse(Set.of())
                .stream()
                .map(e -> Optional.ofNullable(receiptStatus.get(e)).orElseThrow())
                .collect(Collectors.toSet());
    }

    /**
     * Resolve receipt with master search.
     *
     * @param receiptSearchRequest the receipt search request
     * @param master               the master
     * @return the receipt with master search
     */
    public ReceiptWithMasterSearch resolve(ReceiptWithMasterSearchRequest receiptSearchRequest, User master) {

        String sortValue = getValueOrDefault(receiptSearchRequest.getSort(),"");

        Sort sort = getSort(receiptSort, sortValue);

        Set<ReceiptStatusEnum> receiptStatuses = getReceiptStatusEnums(receiptSearchRequest.getStatus());

        return ReceiptWithMasterSearch.builder()
                .receiptStatuses(receiptStatuses)
                .customerUsername(receiptSearchRequest.getCustomer())
                .master(master)
                .pageRequest(PageRequest.of(getValueOrZero(receiptSearchRequest.getPage()), getValueOrDefault(receiptSearchRequest.getCount(), 5), sort))
                .build();
    }

    /**
     * Resolve user search.
     *
     * @param userSearchRequest the user search request
     * @return the user search
     */
    public UserSearch resolve(UserSearchRequest userSearchRequest) {

        String sortValue = Optional.ofNullable(userSearchRequest.getSort()).orElse("");

        Sort sort = getSort(userSort, sortValue);

        return UserSearch.builder()
                .username(userSearchRequest.getUsername())
                .pageRequest(PageRequest.of(getValueOrZero(userSearchRequest.getPage()), getValueOrDefault(userSearchRequest.getCount(), 5), sort))
                .build();
    }

    /**
     * Resolve master search.
     *
     * @param masterSearchRequest the master search request
     * @return the master search
     */
    public MasterSearch resolve(MasterSearchRequest masterSearchRequest) {

        String sortValue = getValueOrDefault(masterSearchRequest.getSort(),"");

        Sort sort = getSort(userSort, sortValue);

        return MasterSearch.builder()
                .username(masterSearchRequest.getUsername())
                .pageRequest(PageRequest.of(getValueOrZero(masterSearchRequest.getPage()), getValueOrDefault(masterSearchRequest.getCount(), 5), sort))
                .build();
    }

    private Integer getValueOrZero(Integer value) {
        return getValueOrDefault(value, 0);
    }

    private <T> T getValueOrDefault(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
}

