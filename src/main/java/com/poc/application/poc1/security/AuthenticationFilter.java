package com.poc.application.poc1.security;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.poc.application.poc1.auth.AuthenticatedUserDetails;
import com.poc.application.poc1.auth.Authority;
import com.poc.application.poc1.auth.BasedSecurityContext;
import com.poc.application.poc1.dao.UserDao;
import com.poc.application.poc1.entity.User;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;



@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {



   @Context
    private ResourceInfo resourceInfo;
    
    UserDao userDao = new UserDao();
    
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    



   @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        final List<String> authorization = headers.get("Authorization");
        final String encodedUserPassword = authorization.get(0).replaceFirst("Basic" + " ", "");
        
        if (authorizationHeader == null && encodedUserPassword.isEmpty()) {
             requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                     .entity("Access blocked for all users !!").build());
            return;
        }
           
        //Decode username and password
        String usernameAndPassword = new String( Base64.getDecoder().decode(encodedUserPassword.getBytes()));



       //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final Long username =  Long.valueOf(tokenizer.nextToken()) ;
        final String password = tokenizer.nextToken();
          
        //Verifying Username and password
        System.out.println(username);
        System.out.println(password);
        handleTokenBasedAuthentication(username, requestContext, password);



       // Other authentication schemes (such as Basic) could be supported
    }



   private void handleTokenBasedAuthentication(Long authenticationToken, ContainerRequestContext requestContext, String password) {



       User user = userDao.findByUserId(authenticationToken);
      
        if (user == null || !user.getPassword().equalsIgnoreCase(password)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Invalid  users and password !!").build());
           return;
       }
        
        Set<Authority> authorities = new HashSet<Authority>();
        
        if(user.getRole().equalsIgnoreCase("ADMIN"))
           authorities.add(Authority.ADMIN);
        else if(user.getRole().equalsIgnoreCase("PRODUCER"))
            authorities.add(Authority.PRODUCER);
        else if(user.getRole().equalsIgnoreCase("CONSUMER"))
            authorities.add(Authority.CONSUMER);
        else
            authorities.add(Authority.USER);
        
        AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(user.getName(), authorities);



       boolean isSecure = requestContext.getSecurityContext().isSecure();
        SecurityContext securityContext = new BasedSecurityContext(authenticatedUserDetails, isSecure);
        requestContext.setSecurityContext(securityContext);
    }
}