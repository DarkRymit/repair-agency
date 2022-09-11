package com.epam.finalproject.util;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public interface SecurityUtils {


    static boolean hasRole(Authentication auth, String role) {
        return isAuthenticated(auth) && auth.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_" + role));
    }

    static boolean hasRole(UserDetails userDetails, String role) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(s -> s.equals("ROLE_" + role));
    }

    static boolean isAnonymous(Authentication auth) {
        return auth == null || !auth.isAuthenticated();
    }


    static boolean isAuthenticated(Authentication auth) {
        return auth != null && auth.isAuthenticated();
    }


}
