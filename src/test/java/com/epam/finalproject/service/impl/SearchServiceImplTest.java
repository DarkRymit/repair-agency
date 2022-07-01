package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.UserSearch;
import com.epam.finalproject.payload.request.search.ReceiptSearchRequest;
import com.epam.finalproject.payload.request.search.UserSearchRequest;
import com.epam.finalproject.repository.ReceiptRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.util.SearchRequestResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SearchServiceImplTest {

    @Mock
    ReceiptRepository receiptRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    SearchRequestResolver searchRequestResolver;

    @InjectMocks
    SearchServiceImpl searchService;

    Page<User> userPage;

    Page<Receipt> receiptPage;

    ReceiptSearch receiptSearch;

    UserSearch userSearch;

    @BeforeEach
    void setMockOutput() {
        User user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(new HashSet<>(Set.of(new Role(RoleEnum.CUSTOMER))))
                .build();
        userPage = new PageImpl<>(List.of(user));
        Receipt receipt = Receipt.builder()
                .id(1L)
                .user(user)
                .note("test")
                .build();
        receiptPage = new PageImpl<>(List.of(receipt));

        receiptSearch = ReceiptSearch.builder().build();
        userSearch = UserSearch.builder().build();
    }

    @Test
    void findBySearchUsers() {
        when(userRepository.findAll((Specification<User>) any(), (Pageable) any())).thenReturn(userPage);

        Page<User> foundPage = searchService.findBySearch(userSearch);
        assertEquals(userPage,foundPage);
    }

    @Test
    void findBySearchRequestUsers() {
        when(searchRequestResolver.resolve((UserSearchRequest) any())).thenReturn(userSearch);
        when(userRepository.findAll((Specification<User>) any(), (Pageable) any())).thenReturn(userPage);

        UserSearchRequest request = new UserSearchRequest("",Set.of(),"",0,1);
        Page<User> foundPage = searchService.findBySearch(request);
        assertEquals(userPage,foundPage);
    }

    @Test
    void findBySearchReceipts() {
        when(receiptRepository.findAll((Specification<Receipt>) any(), (Pageable) any())).thenReturn(receiptPage);

        Page<Receipt> foundPage = searchService.findBySearch(receiptSearch);
        assertEquals(receiptPage,foundPage);
    }

    @Test
    void findBySearchRequestReceipts() {
        when(searchRequestResolver.resolve((ReceiptSearchRequest) any())).thenReturn(receiptSearch);
        when(receiptRepository.findAll((Specification<Receipt>) any(), (Pageable) any())).thenReturn(receiptPage);

        ReceiptSearchRequest request = new ReceiptSearchRequest("",Set.of(),"","",0,1);
        Page<Receipt> foundPage = searchService.findBySearch(request);
        assertEquals(receiptPage,foundPage);

    }
}