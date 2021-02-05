package com.thiago.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiago.dscatalog.entities.Category;
import com.thiago.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public CategoryService() {
		
	}
	
	public List<Category> findAll(){
		return repository.findAll();
	}
	
}
