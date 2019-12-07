package com.educacaoweb.course.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacaoweb.course.entities.Order;
import com.educacaoweb.course.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByClient(User client);

}