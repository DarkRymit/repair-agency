package com.epam.finalproject.entity;

import javax.persistence.*;
import lombok.Data;

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

    @Column(precision=19, scale=4)
    private BigDecimal priceAmount;


    @Column
    private String priceCurrency;

    @Column(nullable = false)
    private LocalDateTime time;

    private String note;
}
