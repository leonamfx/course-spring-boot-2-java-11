package com.educacaoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacaoweb.course.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}