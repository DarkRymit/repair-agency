package com.epam.finalproject.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "receipt_deliveries")
@Data

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
}
