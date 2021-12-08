package com.example.demo.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.http.HttpResponse;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.example.demo.entity.Customer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
//	@Test
//	@Sql("./test.sql")
//	public void getCustomerTest() {
//		ResponseEntity<Customer> responseEntity = testRestTemplate.getForEntity("/customers/view/1", Customer.class);
//		assertEquals(1, responseEntity.getBody().getId());
//		assertEquals("HUY", responseEntity.getBody().getName());
//		assertEquals("2000-01-01", responseEntity.getBody().getDob());
//		assertEquals(1, responseEntity.getBody().getAge());
//	}
//	
	@Test
	public void saveCustomer() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setAge(1);
		customer.setDob(new Date());
		customer.setName("HUY");
		HttpEntity<Customer> request	 = new HttpEntity<>(customer);
		ResponseEntity<Customer> response = testRestTemplate.postForEntity("/customer/add", request, Customer.class);
		assertNotNull(response.getBody().getId());
		assertEquals(0, response.getBody().getId());
		assertEquals(null, response.getBody().getName());
		assertEquals(null, response.getBody().getDob());
		assertEquals(0, response.getBody().getAge());
	}
}
