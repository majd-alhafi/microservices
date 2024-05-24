package com.shophub.product_service;

import com.shophub.product_service.dto.ProductRequest;
import com.shophub.product_service.repositories.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	// Define a static MongoDBContainer to use Testcontainers' MongoDB instance for testing
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");

	// Autowire MockMvc to perform HTTP requests in the test context
	@Autowired
	private MockMvc mockMvc;

	// Autowire ProductRepository to interact with the database
	@Autowired
	private ProductRepository productRepository;

	// Set dynamic properties for the Spring context, particularly the MongoDB URI
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	// Define a test method to verify that a product can be created successfully
	@Test
	void shouldCreateProduct() throws Exception {
		// Create an ObjectMapper instance to convert objects to JSON
		ObjectMapper mapper = new ObjectMapper();
		// Create a ProductRequest object to be used as the request payload
		ProductRequest request = getProductRequest();
		// Perform a POST request to the /api/product endpoint with the ProductRequest payload
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(request)))
				// Expect the HTTP status to be 201 Created
				.andExpect(status().isCreated());
		// Assert that the product repository contains exactly one product after the request
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	// Helper method to create a ProductRequest object with predefined values
	private static ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("name")
				.description("description")
				.price(BigDecimal.valueOf(33.44))
				.build();
	}
}