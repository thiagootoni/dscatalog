package com.thiago.dscatalog.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.thiago.dscatalog.dto.UserInsertDTO;
import com.thiago.dscatalog.dto.UserDTO;
import com.thiago.dscatalog.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource{

	@Autowired
	private UserService service;

	
	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAllPaginate(
			@RequestParam (value = "page", defaultValue = "0") Integer page, 
			@RequestParam (value = "linesPerPage", defaultValue = "12") Integer linesPerPage, 
			@RequestParam (value = "orderBy", defaultValue = "firstName") String orderBy,
			@RequestParam (value = "direction", defaultValue = "ASC") String direction) {
		
		Page<UserDTO> users = this.service.findAllPaginated(page, linesPerPage, direction, orderBy);
		
		return ResponseEntity.ok(users);
		
	}

	public ResponseEntity<List<UserDTO>> findAll(){
		
		List<UserDTO> users = this.service.findAll();		
		return ResponseEntity.ok(users);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findOne(@PathVariable Long id) {
		
		return ResponseEntity.ok(this.service.findById(id));
		
	}

	@PostMapping
	public ResponseEntity<UserDTO> insertOne(@Valid @RequestBody UserInsertDTO newUserDto) {
		
		UserDTO userDTO = this.service.insert(newUserDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(userDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> updateOne(@Valid @RequestBody UserDTO obj, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO> deleteOne(@PathVariable Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
