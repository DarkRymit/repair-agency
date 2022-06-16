package com.epam.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal moneyAmount;

    @Column(nullable = false)
    private String moneyCurrency;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Wallet{" + "id=" + id + ", name='" + name + '\'' + ", moneyAmount=" + moneyAmount + ", moneyCurrency='" + moneyCurrency + '\'' + ", user=" + (user != null ? user.getId() : null) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wallet wallet = (Wallet) o;

        if (!Objects.equals(id, wallet.id)) return false;
        if (!Objects.equals(name, wallet.name)) return false;
        if (!Objects.equals(moneyAmount, wallet.moneyAmount)) return false;
        if (!Objects.equals(moneyCurrency, wallet.moneyCurrency)) return false;
        return user != null ? user.getId().equals(wallet.user.getId()) : wallet.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (moneyAmount != null ? moneyAmount.hashCode() : 0);
        result = 31 * result + (moneyCurrency != null ? moneyCurrency.hashCode() : 0);
        result = 31 * result + (user != null ? user.getId().hashCode() : 0);
        return result;
    }
}
