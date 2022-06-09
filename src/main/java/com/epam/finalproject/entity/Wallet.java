package com.epam.finalproject.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision=19, scale=4)
    private BigDecimal moneyAmount;

    @Column(nullable = false)
    private String moneyCurrency;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", moneyCurrency='" + moneyCurrency + '\'' +
                ", user=" + user.getId() +
                '}';
    }
}
