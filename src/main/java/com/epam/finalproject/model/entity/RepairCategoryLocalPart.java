package com.epam.finalproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private RepairCategory category;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private AppLocale language;
}
