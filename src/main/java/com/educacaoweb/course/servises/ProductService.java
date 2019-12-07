package com.educacaoweb.course.servises;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacaoweb.course.dto.CategoryDTO;
import com.educacaoweb.course.dto.ProductCategoriesDTO;
import com.educacaoweb.course.dto.ProductDTO;
import com.educacaoweb.course.entities.Category;
import com.educacaoweb.course.entities.Product;
import com.educacaoweb.course.repositories.CategoryRepository;
import com.educacaoweb.course.repositories.ProductRepository;
import com.educacaoweb.course.servises.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll();
		return list.stream().map(e-> new ProductDTO(e)).collect(Collectors.toList());
	}
	
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.get();
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO insert(ProductCategoriesDTO  dto) {
		Product entity = dto.toEntity();
		setProductCategories(entity, dto.getCategories());
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductCategoriesDTO  dto) {
		try {
			Product entity = repository.getOne(id);
			updateData(entity, dto); 
			entity = repository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateData(Product entity, ProductCategoriesDTO dto) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());	
		entity.setImgUrl(dto.getImgUrl());

		if(dto.getCategories()!=null && dto.getCategories().size()>0) {
			setProductCategories(entity, dto.getCategories());
		}
	}

	private void setProductCategories(Product entity, List<CategoryDTO> categories) {
		entity.getCategories().clear();

		for(CategoryDTO dto: categories) {
				Category category = categoryRepository.getOne(dto.getId());
				entity.getCategories().add(category);
		}
	}
	
	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
}
