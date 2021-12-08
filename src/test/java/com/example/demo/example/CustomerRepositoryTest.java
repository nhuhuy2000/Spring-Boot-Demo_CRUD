package com.example.demo.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

@DataJpaTest
public class CustomerRepositoryTest {
	@Autowired
	CustomerRepository customerRepository;
	static Customer customer;
	//given
	@BeforeAll
	public static void setup() {
		customer = new Customer();
		customer.setId(0);
		customer.setAge(0);
		customer.setName("Huy");
		customer.setDob(new Date());
	}
	@Test
	void CheckIfCustomerExist() {
		customerRepository.save(customer);
		//when
		boolean result = customerRepository.existsById(customer.getId());
		//then
		assertThat(result).isTrue();
		
	}
}
