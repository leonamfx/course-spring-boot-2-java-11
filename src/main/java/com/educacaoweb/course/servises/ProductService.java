package com.educacaoweb.course.servises;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacaoweb.course.entities.Category;
import com.educacaoweb.course.entities.Product;
import com.educacaoweb.course.repositories.CategoryRepository;
import com.educacaoweb.course.repositories.ProductRepository;

@Service
public class ProductService {
	
	
	@Autowired
	private ProductRepository repository;
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.get();
	}
}
