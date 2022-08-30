package com.poc.application.poc1.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.application.poc1.entity.Item;
import com.poc.application.poc1.namebinder.ItemIdVaidatorBinder;

import jakarta.annotation.Priority;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.ReaderInterceptorContext;

@Provider
@Priority(value = 1)
@ItemIdVaidatorBinder
public class IteamIdValidatorInterceptor implements ReaderInterceptor {

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {		
		
		 InputStream is = context.getInputStream();
	        String body = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
	        ObjectMapper mapper = new ObjectMapper();
	        try {
	            Item item = mapper.readValue(body, Item.class);
	            if( item.getItemId() != null ||  item.getItemId() != "" ) {
	            	  String regex = "\\b[a-zA-Z]{1,1}\\d{4,5}.?\\b";   
	            	  
	            	  Pattern pattern = Pattern.compile(regex);
	            	  Matcher matcher = pattern.matcher(item.getItemId());
	            	  if(!matcher.matches())
	            	    throw new WebApplicationException(Response.ok().entity("to ensure ItemId starts with alphabet and followed by 5 digits e.g. A12345").build());
	            }
	        } catch (JsonGenerationException | JsonMappingException e) {
	           
	        }
	        InputStream in = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));

	        context.setInputStream(in);
	        return context.proceed();
		
	}
}