package br.edu.roberto.crm.order;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import br.edu.roberto.crm.order.dto.OrderDTO;
import br.edu.roberto.crm.order.dto.OrderPayDTO;
import br.edu.roberto.crm.order.entities.OrderEntity;
import br.edu.roberto.crm.order.repositories.OrderMongoRepository;
import br.edu.roberto.crm.order.service.OrderPaymentClient;
import br.edu.roberto.crm.order.service.OrderService;
import br.edu.roberto.crm.payment.dto.PaymentResponseDTO;
import br.edu.roberto.crm.payment.enums.PaymentMethod;
import br.edu.roberto.crm.payment.enums.PaymentStatus;

class OrderServiceTest {

	static OrderService service;
	static OrderMongoRepository repoMock;
	static OrderPaymentClient paymentClientMock;

	OrderEntity order1 = new OrderEntity(1, 10f, new Date(), 1);
	OrderEntity order2 = new OrderEntity(2, 20f, new Date(), 2);
	OrderEntity order3 = new OrderEntity(3, 30f, new Date(), 3);
	OrderEntity order4 = new OrderEntity(4, 40f, new Date(), 4);
	OrderEntity order5 = new OrderEntity(5, 50f, new Date(), 5);

	OrderPayDTO orderPay3 = new OrderPayDTO(3, PaymentMethod.CARD);
	OrderPayDTO orderPay4 = new OrderPayDTO(4, PaymentMethod.CARD);
	OrderPayDTO orderPay5 = new OrderPayDTO(5, PaymentMethod.CARD);

	@BeforeAll
	public static void setup() {
		repoMock = Mockito.mock(OrderMongoRepository.class);
		paymentClientMock = Mockito.mock(OrderPaymentClient.class);
		service = new OrderService(repoMock, paymentClientMock);
	}
	
	@BeforeEach
	public void resetMocks() {
		Mockito.clearInvocations(repoMock, paymentClientMock);
	}

	@Test
	void testFindByNumber_ExistingOrder() { // Scenario #1
		// Arrange
		Mockito.when(repoMock.findByNumber(1)).thenReturn(Optional.of(order1));

		// Act
		OrderDTO order = service.findByNumber(1);

		// Assert
		assertNotNull(order);
		assertEquals(1, order.getNumber());
		assertEquals(10, order.getValue());

		// confirm the method findById(1) has been called once
		Mockito.verify(repoMock, times(1)).findByNumber(1);
	}

	@Test
	void testFindByNumber_NonExistingOrder() { // Scenario #2
		// Arrange
		Mockito.when(repoMock.findByNumber(2)).thenReturn(Optional.empty());

		try {
			// Act
			OrderDTO order = service.findByNumber(2);

			// Assert
			fail("It should've raised the exception");
		} catch (Exception e) {
			// the exception is expected
		}
	}

	@Test
	void testPayOrder_WithSuccess() { // Scenario #3
		// Arrange
		Mockito.when(repoMock.findByNumber(3)).thenReturn(Optional.of(order1));
		PaymentResponseDTO pendingDTO = new PaymentResponseDTO(3, PaymentStatus.PENDING);
		Mockito.when(paymentClientMock.getPaymentDetail(3)).thenReturn(pendingDTO);

		// Act
		service.payOrder(orderPay3);

		// Assert
		// confirm the methods have been called once
		Mockito.verify(paymentClientMock, times(1)).getPaymentDetail(3);
		Mockito.verify(paymentClientMock, times(1)).confirmPayment(any());
	}

	@Test
	void testPayOrder_WithErrorAlreadyPaid() { // Scenario #4
		// Arrange
		PaymentResponseDTO paidDTO = new PaymentResponseDTO(4, PaymentStatus.PAID);
		Mockito.when(paymentClientMock.getPaymentDetail(4)).thenReturn(paidDTO);
		Mockito.when(repoMock.findByNumber(4)).thenReturn(Optional.of(order4));

		try {
			// Act
			service.payOrder(orderPay4);

			// Assert
			fail("It should've raised the exception");
		} catch (Exception e) {
			// the exception is expected
		}

		// Assert: method getPaymentDetail(...) should be called once
		Mockito.verify(paymentClientMock, times(1)).getPaymentDetail(anyInt());
	}

//	@Test
//	void testPayOrder_WithErrorCanceled() { // Scenario #5
//		// Arrange
//		PaymentResponseDTO canceledDTO = new PaymentResponseDTO(5, PaymentStatus.CANCELED);
//		Mockito.when(paymentClientMock.getPaymentDetail(5)).thenReturn(canceledDTO);
//		Mockito.when(repoMock.findByNumber(5)).thenReturn(Optional.of(order5));
//
//		try {
//			// Act
//			service.payOrder(orderPay5);
//
//			// Assert
//			fail("It should've raised the exception");
//		} catch (Exception e) {
//			// the exception is expected
//		}
//
//		// Assert: method getPaymentDetail(...) should be called once
//		Mockito.verify(paymentClientMock, times(1)).getPaymentDetail(anyInt());
//	}

	@Test
	void testGetAllOrders_WithSuccess() {

		// Arrange
		List<OrderEntity> orders = List.of(order1, order2);
		Mockito.when(repoMock.findAll()).thenReturn(orders);

		// Act
		List<OrderEntity> allOrders = service.findAll();

		// Assert
		Mockito.verify(repoMock, Mockito.times(1)).findAll();
		assertNotNull(allOrders);
		assertEquals(2, allOrders.size());
	}
}
