package com.thiago.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiago.dscatalog.entities.Category;
import com.thiago.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CateoryResource {

	@Autowired
	private CategoryService service;
		
	public CateoryResource() {
		
	}

	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		
		List<Category> allCategories = this.service.findAll();		
		return ResponseEntity.ok().body(allCategories);
		
	}
	
}
