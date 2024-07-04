package br.edu.roberto.crm.order.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.roberto.crm.order.dto.OrderDTO;
import br.edu.roberto.crm.order.dto.OrderNewDTO;
import br.edu.roberto.crm.order.dto.OrderPayDTO;
import br.edu.roberto.crm.order.entities.OrderEntity;
import br.edu.roberto.crm.order.repositories.OrderMongoRepository;
import br.edu.roberto.crm.order.util.OrderEntityConverter;
import br.edu.roberto.crm.payment.dto.NewPaymentDTO;
import br.edu.roberto.crm.payment.dto.ConfirmPaymentDTO;
import br.edu.roberto.crm.payment.dto.PaymentResponseDTO;
import br.edu.roberto.crm.payment.enums.PaymentStatus;
import br.edu.roberto.crm.support.EntityNotFoundException;
import br.edu.roberto.crm.support.InvalidDataException;

@Service
public class OrderService {

	private OrderMongoRepository repo;
	private OrderPaymentClient orderPaymentClient;

	@Autowired
	public OrderService(OrderMongoRepository repo, OrderPaymentClient orderPayClient) {
		this.repo = repo;
		this.orderPaymentClient = orderPayClient;
	}

	public void payOrder(OrderPayDTO dto) {
		OrderDTO order = findByNumber(dto.getNumber());

		PaymentResponseDTO payDTO = orderPaymentClient.getPaymentDetail(dto.getNumber());
		if (PaymentStatus.PAID.equals(payDTO.getStatus())) {
			throw new InvalidDataException("Invalid payment status.");
		}

		ConfirmPaymentDTO confirmPayDTO = new ConfirmPaymentDTO(dto.getNumber(), dto.getMethod(), new Date());
		orderPaymentClient.confirmPayment(confirmPayDTO);
	}

	public void createOrder(OrderNewDTO dto) {
		OrderEntity orderEntity = OrderEntityConverter.toEntity(dto);

		NewPaymentDTO paymentDTO = new NewPaymentDTO(dto.getNumber(), dto.getValue(), new Date());
		PaymentResponseDTO payment = orderPaymentClient.createPayment(paymentDTO);

		orderEntity.setPaymentId(payment.getId());

		repo.save(orderEntity);
	}

	public OrderDTO findByNumber(Integer number) {
		Optional<OrderEntity> obj = repo.findByNumber(number);
		return OrderEntityConverter
				.toDTO(obj.orElseThrow(() -> new EntityNotFoundException("Order " + number + " not found")));
	}

	public List<OrderEntity> findAll() {
		return repo.findAll();
	}
}
