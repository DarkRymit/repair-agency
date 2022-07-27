package com.epam.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "master_responses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptResponse {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Receipt receipt;

    private String text;

    private Integer rating;

}
