package br.edu.roberto.crm.order.dto;

import br.edu.roberto.crm.payment.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderPayDTO {
	
	@NotNull(message = "Order number can not be null.")
	@Min(value = 0, message = "Order number can not be negative.")
	private Integer number;
	
	@NotNull(message = "Payment method can not be null.")
	private PaymentMethod method;
}
