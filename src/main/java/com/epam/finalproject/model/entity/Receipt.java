package com.epam.finalproject.model.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "receipts")
@Data
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "receipt",cascade = CascadeType.ALL ,orphanRemoval=true)
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

    @CreatedDate
    @Column(nullable = false)
    private Instant creationDate;

    @Column
    @CreatedBy
    private String createdBy;

    @Column
    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    @Column
    private Instant lastModifiedDate;

    private String note;
}
