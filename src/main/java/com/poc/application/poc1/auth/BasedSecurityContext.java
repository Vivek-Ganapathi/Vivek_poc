package com.poc.application.poc1.auth;

import java.security.Principal;

import jakarta.ws.rs.core.SecurityContext;


public class BasedSecurityContext implements SecurityContext {

    private AuthenticatedUserDetails authenticatedUserDetails;
   
    private final boolean secure;

    public BasedSecurityContext(AuthenticatedUserDetails authenticatedUserDetails, boolean secure) {
        this.authenticatedUserDetails = authenticatedUserDetails;
        this.secure = secure;
    }

    @Override
    public Principal getUserPrincipal() {
        return authenticatedUserDetails;
    }

    @Override
    public boolean isUserInRole(String s) {
        return authenticatedUserDetails.getAuthorities().contains(Authority.valueOf(s));
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }
}