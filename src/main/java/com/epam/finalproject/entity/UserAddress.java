package com.epam.finalproject.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_addresses")
@Data
@NoArgsConstructor

public class UserAddress {

    @Id
    @Column(name = "user_id", unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    @MapsId
    private User user;

    @Column(nullable = false)
    private String country;

    private String state;

    private String city;

    @Column(nullable = false)
    private String localAddress;

    private String postalCode;

    @Override
    public String toString() {
        return "UserAddress{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", localAddress='" + localAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAddress that = (UserAddress) o;

        if (!Objects.equals(id, that.id)) return false;
        if (user != null ? !user.getId().equals(that.user.getId()) : that.user != null) return false;
        if (!Objects.equals(country, that.country)) return false;
        if (!Objects.equals(state, that.state)) return false;
        if (!Objects.equals(city, that.city)) return false;
        if (!Objects.equals(localAddress, that.localAddress)) return false;
        return Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.getId().hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (localAddress != null ? localAddress.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }
}
