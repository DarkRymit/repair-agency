package com.epam.finalproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "receipt_items")
@Data

public class ReceiptItem {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "receipt_id")
    Receipt receipt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "repair_work_id")
    RepairWork repairWork;

    @Column(precision=19, scale=4)
    private BigDecimal priceAmount;

    @Column
    private String priceCurrency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiptItem that = (ReceiptItem) o;

        if (!Objects.equals(id, that.id)) return false;
        if (receipt != null && receipt.getId() != null ? !receipt.getId().equals(that.receipt.getId()) : that.receipt != null) return false;
        if (!Objects.equals(repairWork, that.repairWork)) return false;
        if (!Objects.equals(priceAmount, that.priceAmount)) return false;
        return Objects.equals(priceCurrency, that.priceCurrency);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (receipt != null && receipt.getId() != null ? receipt.getId().hashCode(): 0);
        result = 31 * result + (repairWork != null ? repairWork.hashCode() : 0);
        result = 31 * result + (priceAmount != null ? priceAmount.hashCode() : 0);
        result = 31 * result + (priceCurrency != null ? priceCurrency.hashCode() : 0);
        return result;
    }
}
