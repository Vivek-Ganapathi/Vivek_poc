package com.poc.application.poc1;

import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.Test;

import com.poc.application.poc1.entity.Item;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

class ItemResourceTest extends JerseyTest {

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(ItemResource.class);
	}

	Item item = new Item();

	@Test
	public void testCreate() {
		Item item = new Item("v34134", "Laptop", "Item type is laptop", 100d);
		Response output = target("/item-resource/create").request()
				.post(Entity.entity(item, MediaType.APPLICATION_JSON));
		System.out.println(output.getStatus());
		assertEquals("Should return status 201", 201, output.getStatus());
	}

	@Test
	public void testGet() {
		Response response = target("/item-resource/item/search").request().get();
		assertEquals("should return status 200", 200, response.getStatus());

	}

	@Test
	public void testUpdate() {
		Item item = new Item("v34133424", "Laptop", " type is laptop", 100d);
		Response output = target("/item-resource/item/v34134").request()
				.put(Entity.entity(item, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 204", 204, output.getStatus());
		System.out.println(output.getStatus());
	}

	@Test
	public void testDelete() {
		Response output = target("/item-resource/item/v34134").request().delete();
		assertEquals("Should return status 202", 202, output.getStatus());
	}
}