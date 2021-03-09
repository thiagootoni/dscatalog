package com.thiago.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.dscatalog.dto.RoleDTO;
import com.thiago.dscatalog.dto.UserDTO;
import com.thiago.dscatalog.dto.UserInsertDTO;
import com.thiago.dscatalog.dto.UserUpdateDTO;
import com.thiago.dscatalog.entities.Role;
import com.thiago.dscatalog.entities.User;
import com.thiago.dscatalog.repositories.RoleRepository;
import com.thiago.dscatalog.repositories.UserRepository;
import com.thiago.dscatalog.services.exception.DataBaseException;
import com.thiago.dscatalog.services.exception.ElementNotFoundException;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEnconder;

	@Autowired
	private UserRepository repo;

	@Autowired
	private RoleRepository roleRepository;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<User> pageUser = this.repo.findAll(pageRequest);

		Page<UserDTO> pageDto = pageUser.map(x -> new UserDTO(x));

		return pageDto;

	}

	@Transactional(readOnly = true)
	public List<UserDTO> findAll() {

		List<User> users = this.repo.findAll();
		/*
		 * List<UserDTO> dtos = new ArrayList<>();
		 * 
		 * users.forEach(x -> dtos.add(new UserDTO(x)));
		 */

		List<UserDTO> dtos = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());

		return dtos;
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {

		User user = this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException("Elemento não encontrado"));

		return new UserDTO(user);

	}

	@Transactional
	public UserDTO insert(UserInsertDTO newUserDto) {

		User newUser = new User();
		copyToEntity(newUserDto, newUser);

		newUser.setPassword(passwordEnconder.encode(newUserDto.getPassword()));

		newUser = this.repo.save(newUser);

		return new UserDTO(newUser);
	}

	@Transactional(readOnly = true)
	private void copyToEntity(UserDTO dto, User user) {
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());

		user.getRoles().clear();

		dto.getRoles().forEach(x -> {
			Role role = this.roleRepository.getOne(x.getId());
			user.getRoles().add(role);
		});

	}

	@Transactional
	public UserDTO updateOne(UserUpdateDTO userUpdateDto, Long id) {

		User user = this.repo.findById(id).orElseThrow(() -> new ElementNotFoundException("Elemento não encontrado."));

		dtoToEntity(userUpdateDto, user);
		user.setPassword(passwordEnconder.encode(userUpdateDto.getPassord()));

		user = this.repo.save(user);

		return new UserDTO(user);

	}

	public void delete(Long id) {

		try {
			this.repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ElementNotFoundException("Elemento não existente.");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Elemento não existente.");
		}

	}

	private void dtoToEntity(UserDTO userDto, User user) {

		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());

		user.getRoles().clear();
		for (RoleDTO r : userDto.getRoles()) {
			Role role = this.roleRepository.getOne(r.getId());
			user.getRoles().add(role);
		}

	}

}
