package com.epam.finalproject.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "receipt_status_flows")
@Data

public class ReceiptStatusFlow {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "from_id")
    ReceiptStatus from;

    @ManyToOne
    @JoinColumn(nullable = false, name = "to_id")
    ReceiptStatus to;

    @ManyToOne
    @JoinColumn(nullable = false, name = "role_id")
    Role role;
}
