package com.educacaoweb.course.servises;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educacaoweb.course.dto.OrderDTO;
import com.educacaoweb.course.dto.OrderItemDTO;
import com.educacaoweb.course.entities.Order;
import com.educacaoweb.course.entities.OrderItem;
import com.educacaoweb.course.entities.Product;
import com.educacaoweb.course.entities.User;
import com.educacaoweb.course.entities.enums.OrderStatus;
import com.educacaoweb.course.repositories.OrderItemRepository;
import com.educacaoweb.course.repositories.OrderRepository;
import com.educacaoweb.course.repositories.ProductRepository;
import com.educacaoweb.course.repositories.UserRepository;
import com.educacaoweb.course.servises.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}
	
	public OrderDTO findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		authService.validadeOwnOrderOrAdmin(entity);
		return new OrderDTO(entity);
	}
	
	public List<OrderDTO> findByClient() {
		User client= authService.authenticated();
		List<Order> list= repository.findByClient(client);

		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());

	}
	
	@Transactional(readOnly=true)
	public List<OrderItemDTO> findItems(Long id) {
		Order order= repository.getOne(id);
		authService.validadeOwnOrderOrAdmin(order);
		Set<OrderItem> set = order.getItems();

		return set.stream().map(e -> new OrderItemDTO(e)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly=true)
	public List<OrderDTO> findByClientId(Long clientId) {
		User client= userRepository.getOne(clientId);
		List<Order> list= repository.findByClient(client);

		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}
	
	@Transactional
	public OrderDTO placeOrder(List<OrderItemDTO> dto) {
		User client= authService.authenticated();
		Order order= new Order(null,Instant.now(),OrderStatus.WAITING_PAYMENT,client);

		for(OrderItemDTO it:dto) {
			Product product=productRepository.getOne(it.getProductId());
			OrderItem item= new OrderItem(order,product,it.getQuantity(),it.getPrice());
			order.getItems().add(item);
		}

		repository.save(order);
		orderItemRepository.saveAll(order.getItems());

		return new OrderDTO(order);
	}
	
}
