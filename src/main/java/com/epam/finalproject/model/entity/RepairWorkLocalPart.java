package com.epam.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "repair_work_local_parts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairWorkLocalPart {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private RepairWork work;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false,name = "language_id")
    private AppLocale language;
}
