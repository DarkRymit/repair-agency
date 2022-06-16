package com.epam.finalproject.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "repair_works")
@Data
@NoArgsConstructor
public class RepairWork {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private RepairCategory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RepairWorkName name;

    @Column(nullable = false, precision=19, scale=4)
    private BigDecimal priceAmount;

    @Column(nullable = false)
    private String priceCurrency;

    @ElementCollection(targetClass = RepairWorkStatus.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "repair_work_has_status",joinColumns = @JoinColumn(name = "repair_work_id"))
    @Enumerated(EnumType.STRING)
    private Set<RepairWorkStatus> statuses;

}
