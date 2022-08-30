package com.poc.application.poc1;

import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.Test;

import com.poc.application.poc1.entity.User;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

class UserResourceTest extends JerseyTest {

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(UserResource.class);
	}


	@Test
	public void testCreate() {
		User user = new User(29l, "Suresh", "Consumer", "Suresh@123");
		Response output = target("/myresource/create").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
		System.out.println(output.readEntity(String.class));
		System.out.println(output.getStatus());
		assertEquals("Should return status 201", 201, output.getStatus());
		
		
	}
	

}
