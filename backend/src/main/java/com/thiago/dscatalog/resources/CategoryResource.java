package com.thiago.dscatalog.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	/*@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		
		List<CategoryDTO> allCategories = this.service.findAll();		
		return ResponseEntity.ok().body(allCategories);
		
	}*/
	
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAllPaginate(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
			){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<CategoryDTO> pages = this.service.findAllPaged(pageRequest);
		return ResponseEntity.ok(pages);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable long id) throws ElementNotFoundException{
		
		CategoryDTO categoryDto = this.service.findById(id);
		return ResponseEntity.ok(categoryDto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO categoryDto){
		
		this.service.insert(categoryDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryDto.getId()).toUri();		
		return ResponseEntity.created(uri).body(categoryDto);		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable long id) throws ElementNotFoundException{
		
		categoryDto = this.service.update(categoryDto, id);
		return ResponseEntity.ok().body(categoryDto);
		
	}
	
	@DeleteMapping(value ="/{id}")
	public ResponseEntity<CategoryDTO> delete(@PathVariable long id) throws ElementNotFoundException{
		
		this.service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
