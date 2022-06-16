package com.epam.finalproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "receipts")
@Data
public class Receipt {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "receipt_status_id")
    private ReceiptStatus receiptStatus;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private User master;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private RepairCategory category;

    @Column(precision=19, scale=4)
    private BigDecimal priceAmount;

    @Column
    private String priceCurrency;

    @Column(nullable = false)
    private LocalDateTime time;

    private String note;
}
