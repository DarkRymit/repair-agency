package com.epam.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "receipt_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptItem {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "repair_work_id")
    RepairWork repairWork;

    @Column(precision=19, scale=4)
    private BigDecimal priceAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "currency_id")
    private AppCurrency priceCurrency;

}
