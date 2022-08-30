package com.poc.application.poc1;

import com.poc.application.poc1.entity.User;
import com.poc.application.poc1.namebinder.EmptyUserValidator;
import com.poc.application.poc1.namebinder.PasswordValidator;
import com.poc.application.poc1.namebinder.UniqueUserId;
import com.poc.application.poc1.service.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("User-Resource")

public class UserResource {

	private UserService userService = new UserService();

	@POST
	@Path("create")
	@Consumes("application/json")
	@Produces("application/json")
	@RolesAllowed({ "ADMIN" })
	@PasswordValidator
	@EmptyUserValidator

	public Response addUser(@Valid @UniqueUserId User user) {
		userService.addUser(user);
		return Response.status(Status.CREATED).entity(user).build();
	}

}
