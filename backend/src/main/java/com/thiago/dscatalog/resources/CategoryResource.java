package com.thiago.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thiago.dscatalog.dto.CategoryDTO;
import com.thiago.dscatalog.services.CategoryService;
import com.thiago.dscatalog.services.exception.ElementNotFoundException;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;
		
	public CategoryResource() {
		
	}

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		
		List<CategoryDTO> allCategories = this.service.findAll();		
		return ResponseEntity.ok().body(allCategories);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable long id) throws ElementNotFoundException{
		
		CategoryDTO categoryDto = this.service.findById(id);
		return ResponseEntity.ok(categoryDto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO categoryDto){
		
		this.service.insert(categoryDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryDto.getId()).toUri();		
		return ResponseEntity.created(uri).body(categoryDto);		
	}
	
}
