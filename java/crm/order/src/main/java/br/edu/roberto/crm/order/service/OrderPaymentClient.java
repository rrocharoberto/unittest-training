package br.edu.roberto.crm.order.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.roberto.crm.payment.dto.ConfirmPaymentDTO;
import br.edu.roberto.crm.payment.dto.NewPaymentDTO;
import br.edu.roberto.crm.payment.dto.PaymentResponseDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@Service
public class OrderPaymentClient {

	private ObjectMapper mapper = new ObjectMapper();
	private final String paymentURL = "http://localhost:8084/api/payment";

	public PaymentResponseDTO createPayment(NewPaymentDTO dto) {
		Response resp = RestAssured.given().body(dto).post(paymentURL);
		try {
			return extractedResponseFromBody(resp);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Fail to create payment: " + dto, e);
		}
	}

	public PaymentResponseDTO confirmPayment(ConfirmPaymentDTO dto) {
		Response resp = RestAssured.given().body(dto).put(paymentURL);
		try {
			return extractedResponseFromBody(resp);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Fail to confirm payment: " + dto, e);
		}
	}

	public PaymentResponseDTO getPaymentDetail(Integer orderNumber) {
		Response resp = RestAssured.get(paymentURL + "/" + orderNumber);
		try {
			return extractedResponseFromBody(resp);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Fail to get payment of orderNumber: " + orderNumber, e);
		}
	}

	private PaymentResponseDTO extractedResponseFromBody(Response resp)
			throws JsonProcessingException, JsonMappingException {
		String jsonBody = resp.getBody().asString();
		PaymentResponseDTO details = mapper.readValue(jsonBody, PaymentResponseDTO.class);
		return details;
	}
}
