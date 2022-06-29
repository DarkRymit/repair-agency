package com.epam.finalproject.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Slf4j
public class User implements Serializable {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "user_has_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Wallet> wallets = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private UserAddress address;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = email;
    }

    public Chainer chainer() {
        return new Chainer(this);
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void deleteRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void addWallet(Wallet wallet) {
        this.wallets.add(wallet);
    }

    public void deleteWallet(Wallet wallet) {
        this.wallets.remove(wallet);
    }

    public static class Chainer {
        User user;

        private Chainer(User user) {
            this.user = user;
        }

        public Chainer username(String username) {
            user.setUsername(username);
            return this;
        }

        public Chainer email(String email) {
            user.setEmail(email);
            return this;
        }

        public Chainer password(String password) {
            user.setPassword(password);
            return this;
        }

        public Chainer firstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Chainer lastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Chainer phone(String phone) {
            user.setPhone(phone);
            return this;
        }

        public Chainer roles(Set<Role> roles) {
            user.setRoles(roles);
            return this;
        }

        public Chainer wallets(Set<Wallet> wallets) {
            user.setWallets(wallets);
            return this;
        }

        public Chainer address(UserAddress address) {
            user.setAddress(address);
            return this;
        }

        public Chainer addRole(Role role) {
            user.addRole(role);
            return this;
        }

        public Chainer addWallet(Wallet wallet) {
            user.addWallet(wallet);
            return this;
        }

        public User getTarget() {
            return user;
        }
    }
}