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
    @ManyToOne
    private RepairWork work;

    @Column(nullable = false, precision=19, scale=4)
    private BigDecimal lowerBorder;

    @Column(nullable = false, precision=19, scale=4)
    private BigDecimal upperBorder;

    @ManyToOne
    @JoinColumn(nullable = false,name = "currency_id")
    private AppCurrency currency;

}
