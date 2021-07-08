package com.thiago.dscatalog.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thiago.dscatalog.dto.ProductDTO;
import com.thiago.dscatalog.entities.Category;
import com.thiago.dscatalog.entities.Product;
import com.thiago.dscatalog.factory.CategoryFactory;
import com.thiago.dscatalog.factory.ProductFactory;
import com.thiago.dscatalog.repositories.CategoryRepository;
import com.thiago.dscatalog.repositories.ProductRepository;
import com.thiago.dscatalog.services.exception.DataBaseException;
import com.thiago.dscatalog.services.exception.ElementNotFoundException;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private ProductRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;

	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 999L;
		dependentId = 5L;
		product = ProductFactory.createProduct();
		category = CategoryFactory.createCategory();
		page = new PageImpl<>(List.of(product));
		
		Mockito
			.when(this.repository.findAllPaginated(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any()))
			.thenReturn(page);
		
		Mockito.when(this.repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(this.repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(this.repository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(this.repository.getOne(existingId)).thenReturn(product);
		Mockito.when(this.repository.getOne(nonExistingId)).thenThrow(new EntityNotFoundException());
		Mockito.when(categoryRepository.getOne(ArgumentMatchers.anyLong())).thenReturn(category);
		
		Mockito.doNothing().when(this.repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(this.repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(this.repository).deleteById(dependentId);
		
	}
	
	@Test
	void updateShouldByReturnElementNotFoundExceptionWhenIdNotExists() {
		assertThrows(ElementNotFoundException.class, ()-> {
			this.service.update(ProductFactory.createProductDTO(), nonExistingId);
		});
	}
	
	@Test
	void updateIdShouldByReturnProductDtoWhenIdExists() {
		ProductDTO productDto = this.service.update(ProductFactory.createProductDTO(), existingId);
		assertEquals(productDto.getClass().getName(), ProductDTO.class.getName());
	}
	
	@Test
	void findByIdShouldByReturnProductDtoWhenIdExists() {
		ProductDTO productDto = this.service.findById(existingId);
		assertEquals(productDto.getClass().getName(), ProductDTO.class.getName());
	}
	
	@Test
	void findByIdShouldByReturnElementNotFoundExceptionWhenIdNotExists() {
		assertThrows(ElementNotFoundException.class, ()-> {
			this.service.findById(nonExistingId);
		});
	}
	
	@Test
	void findAllPagedShouldReturnPageOfProductsDtoWhenNoneCategoryHasInformed() {
		
		Page<ProductDTO> page = this.service.findAllPaged(PageRequest.of(0, 10), "", 0L);		
		assertThat(page).isNotEmpty();		
	}
	
	@Test
	void findAllPagedShouldReturnPageOfProductsDtoWhenSomeCategoryHasInformed() {
		
		Page<ProductDTO> page = this.service.findAllPaged(PageRequest.of(0, 10), "", 3L);
		assertThat(page).isNotEmpty();		
	}

	@Test
	void deleteShouldDoNothingWhenIdExisting() {
		
		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);		
	}
	
	
	@Test
	void deleteShouldThrowElementNotFoundExceptionNothingWhenUsingNonExistingId() {
		
		assertThrows(ElementNotFoundException.class, () ->{
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);		
	}
	
	@Test
	void deleteShouldThrowDataBaseExceptionNothingWhenUsingNonExistingId() {
		
		assertThrows(DataBaseException.class, () ->{
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);		
	}

}
