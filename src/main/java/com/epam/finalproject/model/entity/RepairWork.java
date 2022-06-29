package com.epam.finalproject.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairWork that = (RepairWork) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(category, that.category)) return false;
        if (name != that.name) return false;
        if (!Objects.equals(priceAmount, that.priceAmount)) return false;
        if (!Objects.equals(priceCurrency, that.priceCurrency))
            return false;
        return Objects.equals(statuses, that.statuses);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (priceAmount != null ? priceAmount.hashCode() : 0);
        result = 31 * result + (priceCurrency != null ? priceCurrency.hashCode() : 0);
        result = 31 * result + (statuses != null ? statuses.hashCode() : 0);
        return result;
    }
}
