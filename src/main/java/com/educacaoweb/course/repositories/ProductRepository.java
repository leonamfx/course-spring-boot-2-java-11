package com.educacaoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacaoweb.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
