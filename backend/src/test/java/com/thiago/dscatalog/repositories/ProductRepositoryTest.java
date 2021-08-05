package com.thiago.dscatalog.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.thiago.dscatalog.entities.Category;
import com.thiago.dscatalog.entities.Product;
import com.thiago.dscatalog.factory.ProductFactory;

@DataJpaTest	
class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProductsInTestDatabase;
	private long countProductsWhitinhPcGamerInTheName;
	private long countAllEletronicsProductsInTestDatabase;
	private long idCategory;
	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 999L;
		countTotalProductsInTestDatabase = 25L;
		countProductsWhitinhPcGamerInTheName = 21L;
		countAllEletronicsProductsInTestDatabase = 2L;
		idCategory = 2L;
	}
	
	@Test
	public void findShouldReturnProductWhenNameExists() {
		
		String productName = "PC GAMER";		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<Product> pageResult = this.repository.findAllPaginated(productName, null, pageRequest);		
		assertEquals(countProductsWhitinhPcGamerInTheName, pageResult.getTotalElements());
		
	}
	
	@Test
	public void findShouldReturnProductWhenNameExistsIgnoringCase() {
		
		String productName = "Pc GaMEr";		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<Product> pageResult = this.repository.findAllPaginated(productName, null, pageRequest);		
		assertEquals(countProductsWhitinhPcGamerInTheName, pageResult.getTotalElements());
		
	}
	
	@Test
	public void findShouldReturnAllProductsWhenNameIsEmpty() {
		
		String productName = "";
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<Product> pageResult = this.repository.findAllPaginated(productName, null, pageRequest);		
		assertEquals(countTotalProductsInTestDatabase, pageResult.getTotalElements());
		
	}
	
	@Test
	public void findShouldReturnAllEletronicsProductsWhenSelectedCategoryIsEletronics() {
		
		String productName = "";
		PageRequest pageRequest = PageRequest.of(0, 10);		
		List<Category> categories = Arrays.asList(categoryRepository.getOne(idCategory));
		
		Page<Product> pageResult = this.repository.findAllPaginated(productName, categories, pageRequest);		
		assertEquals(countAllEletronicsProductsInTestDatabase, pageResult.getTotalElements());
		
	}
	
	@Test
	public void saveShouldSaveNewProductWithAutoIncrementIdWhenIdIsNull() {
		
		Product product = ProductFactory.createProduct();
		product.setId(null);		
		assertEquals(countTotalProductsInTestDatabase + 1L, repository.save(product).getId());
		assertNotNull(product.getId());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {		
		
		repository.deleteById(existingId);		
		assertFalse(repository.findById(existingId).isPresent());
	}
	
	@Test
	public void deleteShouldThrowsElementNotFoundExceptionWhenIdDoesNotExist() {
		
		assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}

}
