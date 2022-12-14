package com.epam.finalproject.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,  length = 20)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false,  length = 20)
    private String firstName;

    @Column(nullable = false,  length = 20)
    private String lastName;

    @Column(nullable = false,  length = 32)
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_has_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Wallet> wallets = new HashSet<>();

    @CreatedDate
    @Column(nullable = false)
    private Instant creationDate;

    @Column(length = 20)
    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant lastModifiedDate;

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void deleteRole(Role role) {
        this.roles.remove(role);
    }

    public void addWallet(Wallet wallet) {
        this.wallets.add(wallet);
    }

    public void deleteWallet(Wallet wallet) {
        this.wallets.remove(wallet);
    }

}