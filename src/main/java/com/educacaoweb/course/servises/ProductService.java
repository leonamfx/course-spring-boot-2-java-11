package com.educacaoweb.course.servises;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacaoweb.course.dto.ProductDTO;
import com.educacaoweb.course.entities.Product;
import com.educacaoweb.course.repositories.ProductRepository;

@Service
public class ProductService {
	
	
	@Autowired
	private ProductRepository repository;
	
	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll();
		return list.stream().map(e-> new ProductDTO(e)).collect(Collectors.toList());
	}
	
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.get();
		return new ProductDTO(entity);
	}
}
