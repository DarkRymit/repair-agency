package com.epam.finalproject.util;

import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.UserSearch;
import com.epam.finalproject.payload.request.search.ReceiptSearchRequest;
import com.epam.finalproject.payload.request.search.UserSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchRequestResolverTest {


    SearchRequestResolver searchRequestResolver;

    Map<String, Sort> receiptSort;
    Map<String, Sort> userSort;
    Map<String, ReceiptStatusEnum> receiptStatus;

    {
        HashMap<String, Sort> receiptSortMap = new HashMap<>();

        receiptSortMap.put("create-time", Sort.by("creationDate"));
        receiptSortMap.put("status", Sort.by("receiptStatus"));
        receiptSortMap.put("",  Sort.by("creationDate"));

        receiptSort = Collections.unmodifiableMap(receiptSortMap);

        HashMap<String, ReceiptStatusEnum> receiptStatusMap = new HashMap<>();

        receiptStatusMap.put("done", ReceiptStatusEnum.DONE);
        receiptStatusMap.put("paid", ReceiptStatusEnum.PAID);
        receiptStatusMap.put("cancel", ReceiptStatusEnum.CANCELED);

        receiptStatus = Collections.unmodifiableMap(receiptStatusMap);

        HashMap<String, Sort> userSortMap = new HashMap<>();

        userSortMap.put("username", Sort.by("username"));
        userSortMap.put("name", Sort.by("firstName").and(Sort.by("lastName")));
        userSortMap.put("",  Sort.by("username"));

        userSort =  Collections.unmodifiableMap(userSortMap);

    }

    @BeforeEach
    void setUp() {
        searchRequestResolver=new SearchRequestResolver(receiptSort,userSort,receiptStatus);
    }

    @Test
    void resolveUserRequest() {
        UserSearchRequest request = new UserSearchRequest("",Set.of(),"user",0,10);

        UserSearch userSearch = searchRequestResolver.resolve(request);

        assertEquals("user",userSearch.getUsername());
        PageRequest pageRequest = userSearch.getPageRequest();
        assertEquals(Sort.by("username"),pageRequest.getSort());
        assertEquals(0,pageRequest.getPageNumber());
        assertEquals(10,pageRequest.getPageSize());
    }

    @Test
    void resolveReceiptRequest() {
        ReceiptSearchRequest request = new ReceiptSearchRequest("",Set.of("paid","done"),"","mast",2,15);

        ReceiptSearch receiptSearch = searchRequestResolver.resolve(request);

        assertEquals("mast",receiptSearch.getMasterUsername());
        assertEquals("",receiptSearch.getUserUsername());
        assertThat(receiptSearch.getReceiptStatuses(), containsInAnyOrder(ReceiptStatusEnum.DONE,ReceiptStatusEnum.PAID));
        PageRequest pageRequest = receiptSearch.getPageRequest();
        assertEquals(Sort.by("creationDate"),pageRequest.getSort());
        assertEquals(2,pageRequest.getPageNumber());
        assertEquals(15,pageRequest.getPageSize());
    }
}