package br.edu.roberto.crm.order;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.roberto.crm.order.entities.OrderEntity;
import br.edu.roberto.crm.order.repositories.OrderMongoRepository;

@SpringBootApplication
public class OrderApp implements CommandLineRunner {

	@Autowired
	private OrderMongoRepository orderMongoRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(OrderApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		OrderEntity order1 = new OrderEntity(1, 10f, new Date(), 1);
		OrderEntity order2 = new OrderEntity(2, 20f, new Date(), 2);
		
		//limpa a coleção de pedidos e salva novamente os pedidos
		orderMongoRepo.deleteAll();
		orderMongoRepo.save(order1);
		orderMongoRepo.save(order2);
	}
}
