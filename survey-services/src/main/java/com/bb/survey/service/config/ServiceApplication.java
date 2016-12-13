package com.bb.survey.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import com.bb.survey.dao.domain.Order;
import com.bb.survey.service.OrderService;

@SpringBootApplication
@EnableSpringDataWebSupport
@ComponentScan("com.bb.survey.service.*")
@EntityScan(basePackages = {"com.tanistan.orderservice.repository.model"} )
@EnableJpaRepositories(basePackages = {"com.tanistan.orderservice.repository"})
public class ServiceApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);		
	}
	
	@Autowired
	public OrderService orderService;

	@Override
	public void run(String... arg0) throws Exception {
		Order order = new Order();
		order.setUserId(1001L);
		order.setOrderName("Order-1");
		
		orderService.add(order);
		System.out.println(orderService.findById(1001L));
		
	}
}
