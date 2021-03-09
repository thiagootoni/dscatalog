package com.thiago.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.dscatalog.dto.CategoryDTO;
import com.thiago.dscatalog.dto.ProductDTO;
import com.thiago.dscatalog.entities.Category;
import com.thiago.dscatalog.entities.Product;
import com.thiago.dscatalog.repositories.CategoryRepository;
import com.thiago.dscatalog.repositories.ProductRepository;
import com.thiago.dscatalog.services.exception.DataBaseException;
import com.thiago.dscatalog.services.exception.ElementNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public ProductService() {
		
	}
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(){
		
		List<Product> categories = repository.findAll();
		
		List<ProductDTO> categoriesDto = categories.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		
		return categoriesDto;
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		
		Page<Product> pages = this.repository.findAll(pageRequest);
		
		Page<ProductDTO> pagesDto = pages.map(x -> new ProductDTO(x));
		
		return pagesDto;
		
	}
	

	@Transactional(readOnly = true)
	public ProductDTO findById(long id) throws ElementNotFoundException {
		
		Product category = this.repository.findById(id)
				.orElseThrow(() -> new ElementNotFoundException("Elemento não econtrado."));
		
		return new ProductDTO(category, category.getCategories());
		
	}

	@Transactional
	public ProductDTO insert(ProductDTO productDto) {
		
		// Validações??? 
		
		Product product = new Product(productDto);
		this.repository.save(product);
		productDto.setId(product.getId());
		return productDto;
	}

	@Transactional
	public ProductDTO update(ProductDTO productDto, long id) throws ElementNotFoundException {
		
		try {
			
			Product product = this.repository.getOne(id);			
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setDate(productDto.getDate());
			product.setImgUrl(productDto.getImgUrl());
			product.setPrice(productDto.getPrice());
			
			this.repository.save(product);
			
			return new ProductDTO(product);
			
		} catch (EntityNotFoundException e) {
			throw new ElementNotFoundException("Elemento não existente.");
		}
	}

	
	public void delete(long id) throws ElementNotFoundException {
		
		try {
			this.repository.deleteById(id);		
		} catch (EmptyResultDataAccessException e) {
			throw new ElementNotFoundException("Elemento não existente.");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Elemento não existente.");
		}
	}

	public void dtoToEntity(Product product, ProductDTO productDto) {
		
		product.setId(productDto.getId());
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setImgUrl(productDto.getImgUrl());
		product.setDate(productDto.getDate());
		
		product.getCategories().clear();
		
		for (CategoryDTO catDto : productDto.getCategories()) {
			
			Category category = this.categoryRepository.getOne(catDto.getId());
			product.getCategories().add(category);
		}
		
	}
	
}
