package com.epam.finalproject.security;

import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.RoleEnum;
import com.epam.finalproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    public static final String SPRING_SECURITY_ROLE_MARKER = "ROLE_";
    String username;

    String password;

    Set<GrantedAuthority> authorities;

    public static UserDetailsImpl of(User user) {
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), constructSetAuthoritiesFrom(user));
    }

    private static Set<GrantedAuthority> constructSetAuthoritiesFrom(User user) {
        return constructSetAuthoritiesFrom(user::getRoles);
    }

    private static Set<GrantedAuthority> constructSetAuthoritiesFrom(Supplier<Collection<Role>> roleCollectionSupplier) {
        return roleCollectionSupplier.get()
                .stream()
                .map(role -> new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_MARKER + role.getName().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return authorities.stream()
                .noneMatch(a -> a.getAuthority().equals(SPRING_SECURITY_ROLE_MARKER + RoleEnum.BLOCKED));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return authorities.stream()
                .noneMatch(a -> a.getAuthority().equals(SPRING_SECURITY_ROLE_MARKER + RoleEnum.UNVERIFIED));
    }
}
