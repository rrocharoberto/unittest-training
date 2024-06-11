package br.edu.roberto.crm.payment.dto;

import java.util.Date;

import br.edu.roberto.crm.payment.enums.PaymentStatus;
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
public class PaymentDTO {

	private int id;
	private String method;
	private Date dueDate;
	private float amount;
	private Date paidAt;
	private int transactionId;
	private Boolean active;
	private PaymentStatus status;
}
