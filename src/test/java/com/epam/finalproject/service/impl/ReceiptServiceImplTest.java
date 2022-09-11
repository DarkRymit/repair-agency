package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.ReceiptDeliveryDTO;
import com.epam.finalproject.dto.ReceiptItemDTO;
import com.epam.finalproject.dto.RepairWorkDTO;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.entity.enums.RoleEnum;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ReceiptServiceImplTest {

    static final ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new ModelMapper();
    }

    @Mock
    ReceiptRepository receiptRepository;
    @Mock
    ReceiptStatusRepository receiptStatusRepository;
    @Mock
    ReceiptStatusFlowRepository receiptStatusFlowRepository;
    @Mock
    RepairCategoryRepository repairCategoryRepository;
    @Mock
    RepairWorkRepository repairWorkRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    AppCurrencyRepository appCurrencyRepository;
    @Mock
    WalletRepository walletRepository;
    ReceiptServiceImpl receiptService;
    User user;
    AppCurrency currency;
    private RepairCategory repairCategory;
    private RepairWork repairWork1;
    private RepairWork repairWork2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(404L)
                .username("NotDBStriker")
                .email("notdbstrike@gmail.com")
                .firstName("NotDB")
                .password("secretPassword")
                .lastName("Striker")
                .phone("+380 63 108 7168")
                .roles(new HashSet<>(Set.of(new Role(RoleEnum.CUSTOMER))))
                .build();
        currency = new AppCurrency(1L, "USD");

        repairCategory = new RepairCategory(1L, "notebook", Set.of(), Set.of());
        repairWork1 = RepairWork.builder()
                .id(1L)
                .category(repairCategory)
                .keyName("battery-replace")
                .prices(Set.of(new RepairWorkPrice(null, null, BigDecimal.TEN, null, currency)))
                .build();
        repairWork2 = RepairWork.builder()
                .id(2L)
                .category(repairCategory)
                .keyName("data-recovery")
                .prices(Set.of(new RepairWorkPrice(null, null, BigDecimal.TEN, null, currency)))
                .build();
        receiptService = new ReceiptServiceImpl(receiptRepository, receiptStatusRepository, receiptStatusFlowRepository,
                repairCategoryRepository, repairWorkRepository, appCurrencyRepository, walletRepository, userRepository,
                MODEL_MAPPER);
    }


    @Test
    void createNew_ShouldReturnCorrectReceipt_WhenGiveReceiptCreateRequest() {
        ReceiptDeliveryCreateRequest deliveryCreateRequest = new ReceiptDeliveryCreateRequest("Ukraine", null, "Kyiv",
                "str", null);
        Set<ReceiptItemCreateRequest> receiptItemCreateRequests = Set.of(new ReceiptItemCreateRequest(1L),
                new ReceiptItemCreateRequest(2L));
        ReceiptCreateRequest request = new ReceiptCreateRequest(1L, "USD", receiptItemCreateRequests,
                deliveryCreateRequest, "test note");
        ReceiptStatus receiptStatus = new ReceiptStatus(1L, ReceiptStatusEnum.CREATED);
        Set<RepairWork> repairWorks = Set.of(repairWork1, repairWork2);

        when(userRepository.findByUsername("NotDBStriker")).thenReturn(Optional.of(user));
        when(receiptStatusRepository.findByName(ReceiptStatusEnum.CREATED)).thenReturn(Optional.of(receiptStatus));
        when(repairCategoryRepository.findById(any())).thenReturn(Optional.of(repairCategory));
        when(repairWorkRepository.findById(1L)).thenReturn(
                Optional.of(repairWork1));
        when(repairWorkRepository.findById(2L)).thenReturn(
                Optional.of(repairWork2));
        when(appCurrencyRepository.findByCode(any())).thenReturn(Optional.of(currency));

        ReceiptDTO result = receiptService.createNew(request, "NotDBStriker");
        assertEquals(user.getUsername(), result.getUser().getUsername());
        assertEquals(receiptStatus.getName().name(), result.getStatus().getName());

        assertThat(result.getItems()
                .stream()
                .map(ReceiptItemDTO::getRepairWork)
                .collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(
                repairWorks.stream().map(w -> MODEL_MAPPER.map(w, RepairWorkDTO.class)).collect(Collectors.toSet()));

        ReceiptDeliveryDTO resultDelivery = result.getDelivery();
        assertEquals(deliveryCreateRequest.getCountry(), resultDelivery.getCountry());
        assertEquals(deliveryCreateRequest.getCity(), resultDelivery.getCity());
        assertEquals(deliveryCreateRequest.getLocalAddress(), resultDelivery.getLocalAddress());

        assertEquals("USD", result.getPriceCurrency().getCode());

        assertEquals(repairCategory.getKeyName(), result.getCategory().getKeyName());
        assertEquals(request.getNote(), result.getNote());
    }

    @Test
    void update() {

        ReceiptDeliveryUpdateRequest deliveryRequest = new ReceiptDeliveryUpdateRequest("Ukraine", null, "Kyiv", "str",
                null);

        Set<ReceiptItemUpdateRequest> receiptItemUpdateRequests = Set.of(
                new ReceiptItemUpdateRequest(1L, BigDecimal.valueOf(15)),
                new ReceiptItemUpdateRequest(2L, BigDecimal.TEN));

        ReceiptUpdateRequest request = new ReceiptUpdateRequest(1L, ReceiptStatusEnum.PAID, "master",
                receiptItemUpdateRequests, deliveryRequest, "USD", "new Note");


        Receipt oldReceipt = Receipt.builder()
                .id(1L)
                .items(new HashSet<>())
                .user(user)
                .delivery(new ReceiptDelivery())
                .category(repairCategory)
                .build();

        User master = User.builder().id(2L).username("master").roles(Set.of(new Role(RoleEnum.MASTER))).build();

        ReceiptStatus receiptStatus = new ReceiptStatus(1L, ReceiptStatusEnum.PAID);

        Set<RepairWork> repairWorks = Set.of(repairWork1, repairWork2);

        when(receiptRepository.findById(request.getId())).thenReturn(Optional.of(oldReceipt));

        when(userRepository.findByUsername("master")).thenReturn(Optional.of(master));

        when(receiptStatusRepository.findByName(ReceiptStatusEnum.PAID)).thenReturn(Optional.of(receiptStatus));

        when(repairWorkRepository.findById(1L)).thenReturn(Optional.of(repairWork1));

        when(repairWorkRepository.findById(2L)).thenReturn(Optional.of(repairWork2));

        when(receiptRepository.save(any())).then(returnsFirstArg());
        when(appCurrencyRepository.findByCode(any())).thenReturn(Optional.ofNullable(currency));

        ReceiptDTO result = receiptService.update(request);
        assertEquals(user.getUsername(), result.getUser().getUsername());
        assertEquals(receiptStatus.getName().name(), result.getStatus().getName());

        assertThat(result.getItems()
                .stream()
                .map(ReceiptItemDTO::getRepairWork)
                .collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(
                repairWorks.stream().map(w -> MODEL_MAPPER.map(w, RepairWorkDTO.class)).collect(Collectors.toSet()));

        ReceiptDeliveryDTO resultDelivery = result.getDelivery();
        assertEquals(deliveryRequest.getCountry(), resultDelivery.getCountry());
        assertEquals(deliveryRequest.getCity(), resultDelivery.getCity());
        assertEquals(deliveryRequest.getLocalAddress(), resultDelivery.getLocalAddress());

        assertEquals("USD", result.getPriceCurrency().getCode());
        assertThat(result.getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(25));

        assertEquals(repairCategory.getKeyName(), result.getCategory().getKeyName());
        assertEquals(request.getNote(), result.getNote());


    }
}