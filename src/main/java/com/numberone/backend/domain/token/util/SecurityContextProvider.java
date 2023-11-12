package com.numberone.backend.domain.token.util;

import com.numberone.backend.exception.badrequest.BadUserAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityContextProvider {

    public static String getAuthenticatedUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (Objects.isNull(principal)){
            throw new BadUserAuthenticationException();
        }
        return (String) principal;
    }
}
