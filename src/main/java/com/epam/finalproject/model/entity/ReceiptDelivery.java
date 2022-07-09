package com.epam.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;

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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
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
