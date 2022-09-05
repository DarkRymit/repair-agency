package com.epam.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "repair_work_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairWorkPrice {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private RepairWork work;

    @Column(nullable = true, precision=19, scale=4)
    private BigDecimal lowerBorder;

    @Column(nullable = true, precision=19, scale=4)
    private BigDecimal upperBorder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "currency_id")
    private AppCurrency currency;

}
