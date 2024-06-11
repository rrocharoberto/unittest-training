package br.edu.roberto.crm.order.dto;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderNewDTO {
	
	@NotNull(message = "Order number can not be null.")
	@Min(value = 0, message = "Order number can not be negative.")
	private Integer number;
	
	@NotNull(message = "Value can not be null.")
	@Min(value = 0, message = "Value can not be negative.")
	private Float value;
	
	@NotNull(message = "Due date can not be null.")
	private Date dueDate;
}
