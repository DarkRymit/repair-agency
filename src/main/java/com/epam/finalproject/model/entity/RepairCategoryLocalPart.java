package com.epam.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "repair_category_local_parts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairCategoryLocalPart {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RepairCategory category;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private AppLocale language;
}
