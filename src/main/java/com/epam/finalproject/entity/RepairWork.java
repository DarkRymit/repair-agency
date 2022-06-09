package com.epam.finalproject.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;

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
    @JoinColumn(name = "repair_work_id")
    private RepairWork parentID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision=19, scale=4)
    private BigDecimal priceAmount;

    @Column(nullable = false)
    private String priceCurrency;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "repair_work_has_repair_work_status", joinColumns = @JoinColumn(name = "repair_work_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "repair_work_status_id", referencedColumnName = "id"))
    private Collection<RepairWorkStatus> statuses;

}
