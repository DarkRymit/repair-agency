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

    public static final String SpringSecurityRoleMarker = "ROLE_";
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
                .map(role -> new SimpleGrantedAuthority(SpringSecurityRoleMarker + role.getName().name()))
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
        return true;//roles.stream().map(Role::getName).noneMatch(roleEnum -> roleEnum.equals(RoleEnum.// BLOCKED));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;//roles.stream().map(Role::getName).noneMatch(eRole -> eRole.equals(RoleEnum.UNVERIFIED));
    }
}
