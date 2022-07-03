package com.epam.finalproject.service.impl;

import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.entity.enums.RepairCategoryName;
import com.epam.finalproject.model.entity.enums.RepairWorkName;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.payload.request.SignUpRequest;
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
import org.modelmapper.ModelMapper;
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

    @InjectMocks
    ReceiptServiceImpl receiptService;

    User user;

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

    @Test
    void createNew_ShouldReturnCorrectReceipt_WhenGiveReceiptCreateRequest() {
        ReceiptDeliveryCreateRequest deliveryCreateRequest = new ReceiptDeliveryCreateRequest("Ukraine", null, "Kyiv", "str", null);
        Set<ReceiptItemCreateRequest> receiptItemCreateRequests = Set.of(
                new ReceiptItemCreateRequest(1L, BigDecimal.TEN, "USD"),
                new ReceiptItemCreateRequest(2L, BigDecimal.TEN, "USD")
        );
        ReceiptCreateRequest request = new ReceiptCreateRequest(1L, receiptItemCreateRequests, deliveryCreateRequest, 1L, "test note");
        ReceiptStatus receiptStatus = new ReceiptStatus(1L, ReceiptStatusEnum.CREATED);
        RepairCategory repairCategory = new RepairCategory(1L, RepairCategoryName.NOTEBOOK);
        Set<RepairWork> repairWorks = Set.of(
                new RepairWork(1L, repairCategory, RepairWorkName.BATTERY_REPLACE, BigDecimal.TEN, "USD", null),
                new RepairWork(2L, repairCategory, RepairWorkName.DATA_RECOVERY, BigDecimal.TEN, "USD", null)
        );

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED)).thenReturn(Optional.of(receiptStatus));
        when(repairCategoryRepository.getById(any())).thenReturn(repairCategory);
        when(repairWorkRepository.findById(1L)).thenReturn(Optional.of(repairWorks.stream()
                .filter(e -> e.getId().equals(1L))
                .findFirst()
                .orElseThrow()));
        when(repairWorkRepository.findById(2L)).thenReturn(Optional.of(repairWorks.stream()
                .filter(e -> e.getId().equals(2L))
                .findFirst()
                .orElseThrow()));


        Receipt result = receiptService.createNew(request);
        assertEquals(user, result.getUser());
        assertEquals(receiptStatus, result.getReceiptStatus());

        assertThat(result.getReceiptItems()
                .stream()
                .map(ReceiptItem::getRepairWork)
                .collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(repairWorks);

        ReceiptDelivery resultDelivery = result.getReceiptDelivery();
        assertEquals(deliveryCreateRequest.getCountry(),resultDelivery.getCountry());
        assertEquals(deliveryCreateRequest.getCity(),resultDelivery.getCity());
        assertEquals(deliveryCreateRequest.getLocalAddress(),resultDelivery.getLocalAddress());

        assertEquals("USD", result.getPriceCurrency());
        assertThat(result.getPriceAmount()).isEqualByComparingTo(BigDecimal.TEN.add(BigDecimal.TEN));

        assertEquals(repairCategory, result.getCategory());
        assertEquals(request.getNote(), result.getNote());
    }

    @Test
    void update() {
        ReceiptDeliveryUpdateRequest deliveryUpdateRequest = new ReceiptDeliveryUpdateRequest("Ukraine", null, "Kyiv", "str", null);
        Set<ReceiptItemUpdateRequest> receiptItemUpdateRequests = Set.of(
                new ReceiptItemUpdateRequest(1L, BigDecimal.valueOf(15), "USD"),
                new ReceiptItemUpdateRequest(2L, BigDecimal.TEN, "USD")
        );
        ReceiptUpdateRequest request = new ReceiptUpdateRequest(1L,ReceiptStatusEnum.PAID,2L,receiptItemUpdateRequests,deliveryUpdateRequest,"new Note");
        RepairCategory repairCategory = new RepairCategory(1L, RepairCategoryName.NOTEBOOK);
        Receipt oldReceipt = Receipt.builder()
                .id(1L)
                .receiptItems(new HashSet<>())
                .user(user)
                .receiptDelivery(new ReceiptDelivery())
                .category(repairCategory)
                .build();
        User master = User.builder()
                .id(2L)
                .roles(Set.of(new Role(RoleEnum.MASTER)))
                .build();
        ReceiptStatus receiptStatus = new ReceiptStatus(1L, ReceiptStatusEnum.PAID);
        Set<RepairWork> repairWorks = Set.of(
                new RepairWork(1L, repairCategory, RepairWorkName.BATTERY_REPLACE, BigDecimal.TEN, "USD", null),
                new RepairWork(2L, repairCategory, RepairWorkName.DATA_RECOVERY, BigDecimal.TEN, "USD", null)
        );

        when(receiptRepository.findById(request.getId())).thenReturn(Optional.of(oldReceipt));
        when(userRepository.findById(2L)).thenReturn(Optional.of(master));
        when(receiptStatusRepository.findByName(ReceiptStatusEnum.PAID)).thenReturn(Optional.of(receiptStatus));
        when(repairWorkRepository.findById(1L)).thenReturn(Optional.of(repairWorks.stream()
                .filter(e -> e.getId().equals(1L))
                .findFirst()
                .orElseThrow()));
        when(repairWorkRepository.findById(2L)).thenReturn(Optional.of(repairWorks.stream()
                .filter(e -> e.getId().equals(2L))
                .findFirst()
                .orElseThrow()));
        when(receiptRepository.save(any())).then(returnsFirstArg());

        Receipt result = receiptService.update(request);
        assertEquals(user, result.getUser());
        assertEquals(receiptStatus, result.getReceiptStatus());

        assertThat(result.getReceiptItems()
                .stream()
                .map(ReceiptItem::getRepairWork)
                .collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(repairWorks);

        ReceiptDelivery resultDelivery = result.getReceiptDelivery();
        assertEquals(deliveryUpdateRequest.getCountry(),resultDelivery.getCountry());
        assertEquals(deliveryUpdateRequest.getCity(),resultDelivery.getCity());
        assertEquals(deliveryUpdateRequest.getLocalAddress(),resultDelivery.getLocalAddress());

        assertEquals("USD", result.getPriceCurrency());
        assertThat(result.getPriceAmount()).isEqualByComparingTo(BigDecimal.valueOf(25));

        assertEquals(repairCategory, result.getCategory());
        assertEquals(request.getNote(), result.getNote());


    }
}