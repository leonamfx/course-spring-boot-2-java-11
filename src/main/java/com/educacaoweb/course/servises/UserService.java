package com.educacaoweb.course.servises;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educacaoweb.course.dto.UserDTO;
import com.educacaoweb.course.dto.UserInsertDTO;
import com.educacaoweb.course.entities.User;
import com.educacaoweb.course.repositories.UserRepository;
import com.educacaoweb.course.servises.exceptions.DatabaseException;
import com.educacaoweb.course.servises.exceptions.ResourceNotFoundException;

@Service
public class UserService implements  UserDetailsService {
	
	@Autowired
	private BCryptPasswordEncoder  passwordEncode;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AuthService authService;
	
	
	public List<UserDTO> findAll() {
		List<User> list = repository.findAll();
		return list.stream().map(e -> new UserDTO(e)).collect(Collectors.toList());
	}
	
	public UserDTO findById(Long id) {
		authService.validateSelfOrAdmin(id);
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new UserDTO(entity);
	}
	
	public UserDTO insert(UserInsertDTO dto) {
		User entity = dto.toEntity();
		entity.setPassword(passwordEncode.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}
	
	public void delete(Long id) {
		try {
		repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {


		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		authService.validateSelfOrAdmin(id);
		try {
		User entity = repository.getOne(id);
		updateData(entity, dto);
		entity = repository.save(entity);
		return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, UserDTO dto) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setPhone(dto.getPhone());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user= repository.findByEmail(username);

		if(user == null) {
			throw new UsernameNotFoundException(username);
		}

		return user;
	}
	

}
