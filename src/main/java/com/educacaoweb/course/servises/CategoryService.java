package com.educacaoweb.course.servises;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educacaoweb.course.entities.Category;
import com.educacaoweb.course.entities.User;
import com.educacaoweb.course.repositories.CategoryRepository;
import com.educacaoweb.course.servises.exceptions.DatabaseException;
import com.educacaoweb.course.servises.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
		
	}
	
	public Category insert(Category obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
		repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {


		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Category update(Long id, Category obj) {
		try {
		Category entity = repository.getOne(id);
		updateData(entity, obj);
		return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateData(Category entity, Category obj) {
		entity.setName(obj.getName());
	}
	

	
	
	
}
