package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptDelivery;
import com.epam.finalproject.model.entity.ReceiptItem;
import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.payload.request.receipt.create.ReceiptCreateRequest;
import com.epam.finalproject.payload.request.receipt.create.ReceiptDeliveryCreateRequest;
import com.epam.finalproject.payload.request.receipt.create.ReceiptItemCreateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptDeliveryUpdateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptItemUpdateRequest;
import com.epam.finalproject.payload.request.receipt.update.ReceiptUpdateRequest;
import com.epam.finalproject.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ReceiptServiceImplTest {

    @Mock
    ReceiptRepository receiptRepository;

    @Mock
    ReceiptStatusRepository receiptStatusRepository;

    @Mock
    RepairCategoryRepository repairCategoryRepository;

    @Mock
    RepairWorkRepository repairWorkRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    AppCurrencyRepository appCurrencyRepository;

    @InjectMocks
    ReceiptServiceImpl receiptService;

    User user;

    AppCurrency currency;

    @BeforeEach
    void setMockOutput() {
        user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(new HashSet<>(Set.of(new Role(RoleEnum.CUSTOMER), new Role(RoleEnum.UNVERIFIED))))
                .build();
        currency = new AppCurrency(1L,"USD");
    }

    @Test
    void findAll_ShouldReturnListOfReceipt_WhenFindAll() {
        List<Receipt> expected = List.of(
                Receipt.builder()
                        .id(1L)
                        .note("note")
                        .creationDate(Instant.now().minusSeconds(1000))
                        .build(),
                Receipt.builder()
                        .id(2L)
                        .note("note2")
                        .creationDate(Instant.now())
                        .build()
        );
        when(receiptRepository.findAll(any(Sort.class))).thenReturn(expected);
        List<Receipt> receiptList = receiptService.findAll();
        assertFalse(receiptList.isEmpty());
        assertThat(receiptList).containsExactlyElementsOf(expected);
    }

    @Test
    void findAll_ShouldReturnPageOfReceipt_WhenFindAll() {
        List<Receipt> receipts = List.of(
                Receipt.builder()
                        .id(1L)
                        .note("note")
                        .creationDate(Instant.now().minusSeconds(1000))
                        .build(),
                Receipt.builder()
                        .id(2L)
                        .note("note2")
                        .creationDate(Instant.now())
                        .build()
        );
        Pageable pageable = PageRequest.of(0, 10);
        Page<Receipt> expectedReceiptPage = new PageImpl<>(receipts, pageable, 100);
        when(receiptRepository.findAll(any(Pageable.class))).thenReturn(expectedReceiptPage);
        Page<Receipt> receiptPage = receiptService.findAll(pageable);
        assertFalse(receiptPage.isEmpty());
        assertEquals(expectedReceiptPage, receiptPage);
        assertThat(receiptPage.getContent()).containsExactlyElementsOf(receipts);
    }

//    @Test
//    void createNew_ShouldReturnCorrectReceipt_WhenGiveReceiptCreateRequest() {
//        ReceiptDeliveryCreateRequest deliveryCreateRequest = new ReceiptDeliveryCreateRequest("Ukraine", null, "Kyiv", "str", null);
//        Set<ReceiptItemCreateRequest> receiptItemCreateRequests = Set.of(
//                new ReceiptItemCreateRequest(1L, BigDecimal.TEN, "USD"),
//                new ReceiptItemCreateRequest(2L, BigDecimal.TEN, "USD")
//        );
//        ReceiptCreateRequest request = new ReceiptCreateRequest(1L, receiptItemCreateRequests, deliveryCreateRequest, 1L, "test note");
//        ReceiptStatus receiptStatus = new ReceiptStatus(1L, ReceiptStatusEnum.CREATED);
//        RepairCategory repairCategory = new RepairCategory(1L,"notebook",Set.of());
//        Set<RepairWork> repairWorks = Set.of(
//                RepairWork.builder()
//                        .id(1L)
//                        .category(repairCategory)
//                        .keyName("battery-replace")
//                        .localParts(Set.of(new RepairWorkLocalPart(null,null,"Replace battery",null)))
//                        .prices(Set.of(new RepairWorkPrice(null,null,BigDecimal.TEN,null,new AppCurrency(null,"USD"))))
//                        .build(),
//                RepairWork.builder()
//                        .id(2L)
//                        .category(repairCategory)
//                        .keyName("data-recovery")
//                        .localParts(Set.of(new RepairWorkLocalPart(null,null,"Replace battery",null)))
//                        .prices(Set.of(new RepairWorkPrice(null,null,BigDecimal.TEN,null,new AppCurrency(null,"USD"))))
//                        .build()
//        );
//
//        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
//        when(receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED)).thenReturn(Optional.of(receiptStatus));
//        when(repairCategoryRepository.getById(any())).thenReturn(repairCategory);
//        when(repairWorkRepository.findById(1L)).thenReturn(Optional.of(repairWorks.stream()
//                .filter(e -> e.getId().equals(1L))
//                .findFirst()
//                .orElseThrow()));
//        when(repairWorkRepository.findById(2L)).thenReturn(Optional.of(repairWorks.stream()
//                .filter(e -> e.getId().equals(2L))
//                .findFirst()
//                .orElseThrow()));
//        when(appCurrencyRepository.findByCode(any())).thenReturn(Optional.ofNullable(currency));
//
//        Receipt result = receiptService.createNew(request);
//        assertEquals(user, result.getUser());
//        assertEquals(receiptStatus, result.getStatus());
//
//        assertThat(result.getItems()
//                .stream()
//                .map(ReceiptItem::getRepairWork)
//                .collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(repairWorks);
//
//        ReceiptDelivery resultDelivery = result.getDelivery();
//        assertEquals(deliveryCreateRequest.getCountry(),resultDelivery.getCountry());
//        assertEquals(deliveryCreateRequest.getCity(),resultDelivery.getCity());
//        assertEquals(deliveryCreateRequest.getLocalAddress(),resultDelivery.getLocalAddress());
//
//        assertEquals("USD", result.getPriceCurrency().getCode());
//        assertThat(result.getTotalPrice()).isEqualByComparingTo(BigDecimal.TEN.add(BigDecimal.TEN));
//
//        assertEquals(repairCategory, result.getCategory());
//        assertEquals(request.getNote(), result.getNote());
//    }
//
//    @Test
//    void update() {
//        ReceiptDeliveryUpdateRequest deliveryUpdateRequest = new ReceiptDeliveryUpdateRequest("Ukraine", null, "Kyiv", "str", null);
//        Set<ReceiptItemUpdateRequest> receiptItemUpdateRequests = Set.of(
//                new ReceiptItemUpdateRequest(1L, BigDecimal.valueOf(15), "USD"),
//                new ReceiptItemUpdateRequest(2L, BigDecimal.TEN, "USD")
//        );
//        ReceiptUpdateRequest request = new ReceiptUpdateRequest(1L,ReceiptStatusEnum.PAID,2L,receiptItemUpdateRequests,deliveryUpdateRequest,"new Note");
//        RepairCategory repairCategory = new RepairCategory(1L, "notebook",Set.of());
//        Receipt oldReceipt = Receipt.builder()
//                .id(1L)
//                .items(new HashSet<>())
//                .user(user)
//                .delivery(new ReceiptDelivery())
//                .category(repairCategory)
//                .build();
//        User master = User.builder()
//                .id(2L)
//                .roles(Set.of(new Role(RoleEnum.MASTER)))
//                .build();
//        ReceiptStatus receiptStatus = new ReceiptStatus(1L, ReceiptStatusEnum.PAID);
//        Set<RepairWork> repairWorks = Set.of(
//                RepairWork.builder()
//                        .id(1L)
//                        .category(repairCategory)
//                        .keyName("battery-replace")
//                        .localParts(Set.of(new RepairWorkLocalPart(null,null,"Replace battery",null)))
//                        .prices(Set.of(new RepairWorkPrice(null,null,BigDecimal.TEN,null,new AppCurrency(null,"USD"))))
//                        .build(),
//                RepairWork.builder()
//                        .id(2L)
//                        .category(repairCategory)
//                        .keyName("data-recovery")
//                        .localParts(Set.of(new RepairWorkLocalPart(null,null,"Replace battery",null)))
//                        .prices(Set.of(new RepairWorkPrice(null,null,BigDecimal.TEN,null,new AppCurrency(null,"USD"))))
//                        .build()
//        );
//
//        when(receiptRepository.findById(request.getId())).thenReturn(Optional.of(oldReceipt));
//        when(userRepository.findById(2L)).thenReturn(Optional.of(master));
//        when(receiptStatusRepository.findByName(ReceiptStatusEnum.PAID)).thenReturn(Optional.of(receiptStatus));
//        when(repairWorkRepository.findById(1L)).thenReturn(Optional.of(repairWorks.stream()
//                .filter(e -> e.getId().equals(1L))
//                .findFirst()
//                .orElseThrow()));
//        when(repairWorkRepository.findById(2L)).thenReturn(Optional.of(repairWorks.stream()
//                .filter(e -> e.getId().equals(2L))
//                .findFirst()
//                .orElseThrow()));
//        when(receiptRepository.save(any())).then(returnsFirstArg());
//        when(appCurrencyRepository.findByCode(any())).thenReturn(Optional.ofNullable(currency));
//
//        Receipt result = receiptService.update(request);
//        assertEquals(user, result.getUser());
//        assertEquals(receiptStatus, result.getStatus());
//
//        assertThat(result.getItems()
//                .stream()
//                .map(ReceiptItem::getRepairWork)
//                .collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(repairWorks);
//
//        ReceiptDelivery resultDelivery = result.getDelivery();
//        assertEquals(deliveryUpdateRequest.getCountry(),resultDelivery.getCountry());
//        assertEquals(deliveryUpdateRequest.getCity(),resultDelivery.getCity());
//        assertEquals(deliveryUpdateRequest.getLocalAddress(),resultDelivery.getLocalAddress());
//
//        assertEquals("USD", result.getPriceCurrency().getCode());
//        assertThat(result.getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(25));
//
//        assertEquals(repairCategory, result.getCategory());
//        assertEquals(request.getNote(), result.getNote());
//
//
//    }
}