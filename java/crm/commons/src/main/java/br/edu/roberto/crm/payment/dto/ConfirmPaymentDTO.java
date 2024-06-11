package br.edu.roberto.crm.payment.dto;

import java.util.Date;

import br.edu.roberto.crm.payment.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ConfirmPaymentDTO {

	private long orderNumber;
	private PaymentMethod method;
	private Date paidAt;
}
