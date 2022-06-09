package com.epam.finalproject.entity;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_addresses")
@Data
@NoArgsConstructor

public class UserAddress {

    @Id
    @Column(name = "user_id", unique = true)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id", unique = true)
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
}
