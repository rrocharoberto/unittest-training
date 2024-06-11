package br.edu.roberto.crm.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.roberto.crm.order.dto.OrderDTO;
import br.edu.roberto.crm.order.dto.OrderNewDTO;
import br.edu.roberto.crm.order.entities.OrderEntity;
import br.edu.roberto.crm.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService service;

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<OrderEntity> getAllOrders() {
		return service.findAll();
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void createOrder(@RequestBody OrderNewDTO order) {
		service.createOrder(order);
	}

	@GetMapping("/{number}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer number) {
		OrderDTO dto = service.findByNumber(number);
		return ResponseEntity.ok().body(dto);
	}
}
