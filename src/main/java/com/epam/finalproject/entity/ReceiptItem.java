package com.epam.finalproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "receipt_items")
@Data

public class ReceiptItem {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "receipt_id")
    Receipt receipt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "repair_work_id")
    RepairWork repairWork;

    @Column(precision=19, scale=4)
    private BigDecimal priceAmount;

    @Column
    private String priceCurrency;

}
