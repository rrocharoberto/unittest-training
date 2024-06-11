package br.edu.roberto.crm.payment.dto;

import java.util.Date;

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
public class NewPaymentDTO {

	private long orderNumber;
	private float value;
	private Date dueDate;
}
