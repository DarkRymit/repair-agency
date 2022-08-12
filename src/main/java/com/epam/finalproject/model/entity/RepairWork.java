package com.epam.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "repair_works")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairWork {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RepairCategory category;

    private String keyName;

    @OneToMany(mappedBy = "work",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepairWorkLocalPart> localParts;

    @OneToMany(mappedBy = "work",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepairWorkPrice> prices;


}
