package com.educacaoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacaoweb.course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
