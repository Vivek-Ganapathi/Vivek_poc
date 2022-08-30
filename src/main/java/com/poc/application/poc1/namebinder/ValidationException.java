package com.poc.application.poc1.namebinder;

import com.poc.application.poc1.auth.ApiErrorDetails;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationException implements ExceptionMapper<jakarta.validation.ValidationException> {
	 
@Override
public Response toResponse(jakarta.validation.ValidationException exception) {
	final StringBuilder strBuilder = new StringBuilder();
	ApiErrorDetails error = new ApiErrorDetails();
	
	error.setMessage(exception.getMessage());
	error.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
	/*
	 * for (ConstraintViolation<?> cv : ((ConstraintViolationException) exception)
	 * .getConstraintViolations()) { strBuilder.append(cv.getPropertyPath().
	 * toString() + " " + cv.getMessage()); }
	 */
	return Response
	.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
	.type(MediaType.APPLICATION_JSON)
	.entity(error)
	.build();
}
}
