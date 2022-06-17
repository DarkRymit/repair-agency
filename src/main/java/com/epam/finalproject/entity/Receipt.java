package com.epam.finalproject.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "receipt",cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<ReceiptItem> receiptItems;

    @OneToOne(mappedBy = "receipt", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private ReceiptDelivery receiptDelivery;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private RepairCategory category;

    @Column(precision=19, scale=4)
    private BigDecimal priceAmount;

    @Column
    private String priceCurrency;

    @Column(nullable = false)
    private LocalDateTime creationTime;

    private String note;
}
