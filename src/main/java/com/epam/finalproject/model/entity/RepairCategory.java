package com.epam.finalproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "repair_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairCategory {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String keyName;

    @OneToMany
    @JoinColumn(name = "repair_categories_id")
    private Set<RepairCategoryLocalPart> localParts;

}
