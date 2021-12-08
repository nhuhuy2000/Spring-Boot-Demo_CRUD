package com.example.demo.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.service.ServiceInterface;

@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {
	
		@Mock
		CustomerRepository customerRepository;
		
		@InjectMocks
		CustomerService customerService;
		public List<Customer> listCustomers;
		@Test
		@Order(1)
		public void test_listCustomers() throws ParseException {
			List<Customer> listCustumers = new ArrayList<Customer>();
			
			listCustumers.add(new Customer(1, "HUY", new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998"), 0));
			listCustumers.add(new Customer(2, "HUY2", new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998"), 2));
			when(customerRepository.findAll()).thenReturn(listCustumers);
			assertEquals(2, customerService.findAll().size());
			
		}
}
