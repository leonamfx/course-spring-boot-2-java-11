package com.educacaoweb.course.servises;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacaoweb.course.dto.PaymentDTO;
import com.educacaoweb.course.entities.Payment;
import com.educacaoweb.course.repositories.PaymentRepository;
import com.educacaoweb.course.servises.exceptions.ResourceNotFoundException;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository repository;

	public List<PaymentDTO> findAll() {
		List<Payment> list = repository.findAll();
		return list.stream().map(e -> new PaymentDTO(e)).collect(Collectors.toList());
	}

	public PaymentDTO findById(Long id) {
		Optional<Payment> obj = repository.findById(id);
		Payment entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new PaymentDTO(entity);
	}
}