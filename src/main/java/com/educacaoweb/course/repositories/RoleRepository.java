package com.educacaoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacaoweb.course.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}