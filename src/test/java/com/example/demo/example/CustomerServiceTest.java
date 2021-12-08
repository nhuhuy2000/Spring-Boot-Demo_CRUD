package com.example.demo.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	
	@Mock
	private CustomerRepository customerRepository;
	static Customer customer;
	private CustomerService customerService;
	//given
		@BeforeAll
		public static void setup() {
			customer = new Customer();
			customer.setId(0);
			customer.setAge(0);
			customer.setName("Huy");
			customer.setDob(new Date());
		}
	@BeforeEach
	void setUp() {
		customerService =  new CustomerService(customerRepository);
		Mockito.when(customerService.findByName(customer.getName())).thenReturn(customer);
	}
	
	@Test
	@Disabled
	void canGetAllCustomers() {
		//when
		customerService.listCustomers();	
		//then
		verify(customerRepository).findAll();
	}
	@Test
	@Disabled
	public void getAllCustomer() {
		
	}
	@Test
	
	public void canAddCustomer() {
		
	
	}
	@Test
	@Disabled
	public void updateCustomer() {
		
	}
}
