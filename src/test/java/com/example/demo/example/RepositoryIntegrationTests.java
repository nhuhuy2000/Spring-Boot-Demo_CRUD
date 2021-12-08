package com.example.demo.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.ServiceInterface;



//@SpringBootTest(classes = {RepositoryIntegrationTests.class})
@DataJpaTest
public class RepositoryIntegrationTests {
	@Autowired
	public TestEntityManager testEntityManager;
	@Autowired 
	public CustomerRepository customerRepository;
	
	@Test
	public void whenFindById_thenReturnCustomer() {
		
		Customer customer = new Customer(28, "huy", null, 0);
		customerRepository.save(customer);
		
		Customer foundCustomer = customerRepository.findById(customer.getId()).get();
		System.out.println(foundCustomer);
		assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
	}
}
