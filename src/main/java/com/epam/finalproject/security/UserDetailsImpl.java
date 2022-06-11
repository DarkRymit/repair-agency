package com.epam.finalproject.security;

import com.epam.finalproject.entity.Role;
import com.epam.finalproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    String username;

    String password;

    Set<GrantedAuthority> authorities;

    public static UserDetailsImpl of(User user) {
        Set<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map((Function<Role, GrantedAuthority>) role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
        return new UserDetailsImpl(user.getUsername(),user.getPassword(),authorities);
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
        return true;//roles.stream().map(Role::getName).noneMatch(roleEnum -> roleEnum.equals(RoleEnum.ROLE_BLOCKED));
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
