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

import com.thiago.dscatalog.dto.ProductDTO;
import com.thiago.dscatalog.services.ProductService;
import com.thiago.dscatalog.services.exception.ElementNotFoundException;

@RestController
@RequestMapping(value = "/products")
public class ProductsResource {

	@Autowired
	private ProductService service;

	public ProductsResource() {

	}

	/*
	 * @GetMapping public ResponseEntity<List<ProductDTO>> findAll(){
	 * 
	 * List<ProductDTO> allCategories = this.service.findAll(); return
	 * ResponseEntity.ok().body(allCategories);
	 * 
	 * }
	 */

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAllPaginate(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "idCategory", defaultValue = "0") Long idCategory,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<ProductDTO> pages = this.service.findAllPaged(pageRequest, name, idCategory);
		return ResponseEntity.ok(pages);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable long id) {

		ProductDTO productDto = this.service.findById(id);
		return ResponseEntity.ok(productDto);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDto) {

		this.service.insert(productDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(productDto);
	}

	/*
	 * @PostMapping(value = "/image") public ResponseEntity<UriDTO>
	 * uploadImage(@RequestParam("file") MultipartFile file){
	 * 
	 * UriDTO uri = service.uploadFile(file); return ResponseEntity.ok(uri); }
	 */

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductDTO productDto, @PathVariable long id) {

		productDto = this.service.update(productDto, id);
		return ResponseEntity.ok().body(productDto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> delete(@PathVariable long id) throws ElementNotFoundException {

		this.service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
