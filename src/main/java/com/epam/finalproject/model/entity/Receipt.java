package com.epam.finalproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private ReceiptStatus status;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private User master;

    @OneToMany(mappedBy = "receipt",fetch = FetchType.EAGER,cascade = CascadeType.ALL ,orphanRemoval=true)
    private Set<ReceiptItem> items;

    @OneToOne(mappedBy = "receipt", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private ReceiptDelivery delivery;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private RepairCategory category;

    @Column(precision=19, scale=4)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(nullable = false,name = "currency_id")
    private AppCurrency priceCurrency;

    private String note;

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

}
