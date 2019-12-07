package com.educacaoweb.course.resourses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacaoweb.course.dto.ProductCategoriesDTO;
import com.educacaoweb.course.dto.ProductDTO;
import com.educacaoweb.course.entities.Product;
import com.educacaoweb.course.servises.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll() {
		List<ProductDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductCategoriesDTO dto){
		ProductDTO newdto = service.update(id, dto);
		return ResponseEntity.ok().body(newdto);
	}
	
}
