package com.epam.finalproject.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "master_responses")
@Data

public class MasterResponse {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(nullable = false, name = "master_id")
    private User master;

    private String text;

    private Integer rating;


}
