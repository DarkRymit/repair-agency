package com.epam.finalproject.model.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_addresses")
@Data
@NoArgsConstructor

public class UserAddress implements Serializable {

    @Id
    @Column(name = "user_id", unique = true)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
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

}
