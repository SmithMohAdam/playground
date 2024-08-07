package com.product_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_service.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Container
	static MongoDBContainer mongo = new MongoDBContainer("mongo:4.0.10");


	@DynamicPropertySource
	static void setProperites(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongo::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest product  = getproductRequest();
		String stringProd = objectMapper.writeValueAsString(product);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(stringProd))
				.andExpect(status().isCreated());

	}
	@Test
	void shouldGetAllProducts() throws Exception {
		//ProductRequest product  = getproductRequest();
		//String stringProd = objectMapper.writeValueAsString(product);
		mockMvc.perform(
						MockMvcRequestBuilders.get("/api/product"))
				.andExpect(status().isOk());

	}

	private ProductRequest getproductRequest() {
		return ProductRequest.builder()
				.name("iPhone 13")
				.description("iPhone 13")
				.price(BigDecimal.valueOf(12000))
				.build();

	}

}
