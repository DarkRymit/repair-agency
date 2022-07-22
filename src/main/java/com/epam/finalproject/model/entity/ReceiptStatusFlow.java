package com.epam.finalproject.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "receipt_status_flows")
@Data

public class ReceiptStatusFlow {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    ReceiptStatus fromStatus;

    @ManyToOne
    @JoinColumn(nullable = false)
    ReceiptStatus toStatus;

    @ManyToOne
    @JoinColumn(nullable = false)
    Role role;
}
