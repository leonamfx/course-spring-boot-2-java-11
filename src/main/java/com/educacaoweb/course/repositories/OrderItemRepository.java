package com.educacaoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacaoweb.course.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
