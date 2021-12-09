package com.example.demo.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.net.http.HttpResponse;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Get_DEMO {

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
	@Autowired
	private CustomerRepository customerRepository;
	@MockBean
	private CustomerService customerService;
	static Customer customer;
	@BeforeAll
	public static void setup() {
		customer = new Customer();
		customer.setId(1);
		customer.setName("HUY");
		customer.setAge(1);
		customer.setDob(new Date());
		
	}
	@AfterEach
	public void tearDown() {
		customerRepository.deleteAll();
	}
	@Test
	@Disabled
	public void saveCustomer() throws Exception {
//		Customer customer = new Customer();
//		customer.setId(1);
//		customer.setAge(1);
//		customer.setDob(new Date());
//		customer.setName("HUY");
//		HttpEntity<Customer> request	 = new HttpEntity<>(customer);
//		ResponseEntity<Customer> response = testRestTemplate.postForEntity("/customer/add", request, Customer.class);
		ResponseEntity<Customer> response1 = testRestTemplate.getForEntity("/customer/add/1", Customer.class);
		assertNotNull(response1.getBody().getId());
//		assertEquals(0, response.getBody().getId());
//		assertEquals(null, response.getBody().getName());
//		assertEquals(null, response.getBody().getDob());
//		assertEquals(0, response.getBody().getAge());
	}
	@Test
	
	@Disabled
	public void testGetRequest() throws Exception  {
	
		
		
		//when
	
		ResponseEntity<Customer> customerEntity = testRestTemplate.getForEntity("/customer/view/1", Customer.class);
		
		//then
//		assertEquals(customerEntity.getStatusCode(), HttpStatus.OK);
		//assertThrows(RuntimeException.class, () -> restTemplate.getForEntity("http://localhost:8080/customers/view/{a}", Customer.class));
		assertNotNull(customerEntity.getBody().getId());
	}
	@Test
	@Disabled
	public void updateCustomer_GoodInput_thenReturns201() throws Exception {
		//given
		Customer customer1 = new Customer();
		customer1.setName("Name update");
		customer1.setAge(9);
		customer1.setDob(new Date());
		TestRestTemplate restTemplate = new TestRestTemplate();
		//when
		ResponseEntity<Customer> customerResponse = restTemplate.exchange("http://localhost:8080/customers/update/1", HttpMethod.PUT, new HttpEntity<>(customer1), Customer.class);
		//then
		//			assertNotNull(customerResponse.getBody());
		assertEquals(customerResponse.getStatusCode(), HttpStatus.CREATED);
//		assertNotNull(customerResponse.getBody());
	}
	@Test
	void canUpdate() {
		//when
		customerService.updateCustomer(customer.getId(), customer);
		verify(customerRepository).deleteAll();
	}
}
