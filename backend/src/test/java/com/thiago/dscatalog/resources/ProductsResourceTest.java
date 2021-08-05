package com.thiago.dscatalog.resources;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.dscatalog.dto.ProductDTO;
import com.thiago.dscatalog.factory.ProductFactory;
import com.thiago.dscatalog.services.ProductService;
import com.thiago.dscatalog.services.exception.DataBaseException;
import com.thiago.dscatalog.services.exception.ElementNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;

	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	private String operatorUsername;
	private String operatorPassword;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	
	
	private ProductDTO existingProductDto;
	private ProductDTO newProductDto;
	private PageImpl<ProductDTO> page;
	
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		
		operatorUsername = "alex@gmail.com";
		operatorPassword = "123456";
		
		newProductDto = ProductFactory.createProductDTO(null);
		existingProductDto = ProductFactory.createProductDTO(existingId);
		
		page = new PageImpl<>(List.of(existingProductDto));
		
		Mockito.when(service.findById(existingId)).thenReturn(existingProductDto);
		Mockito.when(service.findById(nonExistingId)).thenThrow(ElementNotFoundException.class);
		
		Mockito
			.when(service.findAllPaged(ArgumentMatchers.any(), ArgumentMatchers.anyString(), ArgumentMatchers.any()))
			.thenReturn(page);
		
		Mockito.when(service.insert(newProductDto)).thenReturn(existingProductDto);
		
		Mockito.when(service.update(ArgumentMatchers.any(), ArgumentMatchers.eq(existingId))).thenReturn(existingProductDto);
		Mockito.when(service.update(ArgumentMatchers.any(), ArgumentMatchers.eq(nonExistingId))).thenThrow(ElementNotFoundException.class);
		
		Mockito.doNothing().when(service).delete(existingId);
		Mockito.doThrow(ElementNotFoundException.class).when(service).delete(nonExistingId);
		Mockito.doThrow(DataBaseException.class).when(service).delete(dependentId);
		
	}
	
	
	
	void updateShouldReturnOkWhenIdExists() throws Exception {
		
		String accessToken = obtainAccessToken(operatorUsername, operatorPassword);
		String jsonObject = new ObjectMapper().writeValueAsString(newProductDto);
		
		System.out.println(accessToken);
		
		ResultActions result = 
	      mockMvc.perform(put("/products/{id}", existingId)	
				.header("Authorization", "Bearer " + accessToken)
				.content(jsonObject)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
				
	}
	
	
	@Test
	void updateShouldReturnNotFoundWhenIdDoesntExists() throws Exception {
		
		String accessToken = obtainAccessToken(operatorUsername, operatorPassword);
		String jsonObject = new ObjectMapper().writeValueAsString(newProductDto);
		
		System.out.println(accessToken);
		
		ResultActions result = 
	      mockMvc.perform(put("/products/{id}", nonExistingId)	
				.header("Authorization", "Bearer " + accessToken)
				.content(jsonObject)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
				
	}

	@Test
	void findAllPagedShouldReturnPage() throws Exception {
		
		mockMvc.perform(get("/products")	
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").exists());
	}
	
	
	@Test
	void findByIdShouldReturnProductWhenIdExist() throws Exception {
		
		mockMvc.perform(get("/products/{id}", existingId)	
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.id").value(existingId));
	}
	
	@Test
	void findByIdShouldReturn404ErrorWhenIdDoesntExist() throws Exception {
		mockMvc.perform(get("/products/{id}", nonExistingId)	
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		 
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("client_id", clientId);
	    params.add("username", username);
	    params.add("password", password);
	 
	    ResultActions result 
	    	= mockMvc.perform(post("/oauth/token")
	    		.params(params)
	    		.with(httpBasic(clientId, clientSecret))
	    		.accept("application/json;charset=UTF-8"))
	        	.andExpect(status().isOk())
	        	.andExpect(content().contentType("application/json;charset=UTF-8"));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}	

}
