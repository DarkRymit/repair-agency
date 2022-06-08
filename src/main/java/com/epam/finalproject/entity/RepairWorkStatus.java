package com.epam.finalproject.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "repair_work_statuses")
@Data
@NoArgsConstructor
public class RepairWorkStatus {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "statuses")
    private Collection<RepairWork> repairWorks;

}
