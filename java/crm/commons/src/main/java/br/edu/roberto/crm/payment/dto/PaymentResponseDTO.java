package br.edu.roberto.crm.payment.dto;

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
public class PaymentResponseDTO {

	private int id;
	private PaymentStatus status;
}
