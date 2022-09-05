package com.epam.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false,length = 20)
    private String name;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal moneyAmount;

    @ManyToOne
    @JoinColumn(nullable = false,name = "currency_id")
    private AppCurrency moneyCurrency;

}
