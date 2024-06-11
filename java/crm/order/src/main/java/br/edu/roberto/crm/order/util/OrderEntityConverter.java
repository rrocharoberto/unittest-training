package br.edu.roberto.crm.order.util;

import java.util.Date;

import br.edu.roberto.crm.order.dto.OrderDTO;
import br.edu.roberto.crm.order.dto.OrderNewDTO;
import br.edu.roberto.crm.order.entities.OrderEntity;

public class OrderEntityConverter {

	public static OrderEntity toEntity(OrderNewDTO dto) {
		OrderEntity entity = new OrderEntity(dto.getNumber(), dto.getValue(), new Date(), null);
		return entity;
	}

	public static OrderDTO toDTO(OrderEntity entity) {
		return new OrderDTO(entity.getNumber(), entity.getValue());
	}
}
