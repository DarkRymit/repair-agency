package com.epam.finalproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "receipt_deliveries")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDelivery {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(nullable = false, name = "receipt_id")
    Receipt receipt;

    @Column(nullable = false)
    private String country;

    private String state;

    private String city;

    @Column(nullable = false)
    private String localAddress;

    private String postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiptDelivery that = (ReceiptDelivery) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(country, that.country)) return false;
        if (!Objects.equals(state, that.state)) return false;
        if (!Objects.equals(city, that.city)) return false;
        if (!Objects.equals(localAddress, that.localAddress)) return false;
        return Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (localAddress != null ? localAddress.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }
}
