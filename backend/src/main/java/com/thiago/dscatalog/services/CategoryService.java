package com.thiago.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.dscatalog.dto.CategoryDTO;
import com.thiago.dscatalog.entities.Category;
import com.thiago.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public CategoryService() {
		
	}
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		
		List<Category> categories = repository.findAll();
		
		List<CategoryDTO> categoriesDto = categories.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		return categoriesDto;
	}
	
}
