package com.educacaoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacaoweb.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
