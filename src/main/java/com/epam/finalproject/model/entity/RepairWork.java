package com.epam.finalproject.model.entity;

import com.epam.finalproject.model.entity.enums.RepairWorkStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private RepairCategory category;

    private String keyName;

    @ElementCollection(targetClass = RepairWorkStatus.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "repair_work_has_status",joinColumns = @JoinColumn(name = "repair_work_id"))
    @Enumerated(EnumType.STRING)
    private Set<RepairWorkStatus> statuses;

    @OneToMany(mappedBy = "work",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepairWorkLocalPart> localParts;

    @OneToMany(mappedBy = "work",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepairWorkPrice> prices;


}
