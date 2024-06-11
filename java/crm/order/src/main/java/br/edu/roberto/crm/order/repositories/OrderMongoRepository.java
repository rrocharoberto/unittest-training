package br.edu.roberto.crm.order.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.roberto.crm.order.entities.OrderEntity;

//@Repository
public interface OrderMongoRepository extends MongoRepository<OrderEntity, Integer> {

	Optional<OrderEntity> findByNumber(Integer number);
}
