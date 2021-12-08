package com.example.demo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.entity.Customer;
import com.example.demo.example.CustomerControllerIntegrationTest;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;



@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControlerIntegrationTest {
		
		@MockBean
		private CustomerRepository customerRepository;
		
		static Customer customer;
	
		public  void setup() {
			customer = new Customer();
			customer.setId(1);
			customer.setName("HUY");
			customer.setAge(1);
			customer.setDob(new Date());
			customerRepository.save(customer);
		}
		@AfterEach
		public void tearDown() {
			customerRepository.deleteAll();
		}
		@Test
		
		void testGetRequest() {
			TestRestTemplate restTemplate = new TestRestTemplate();
			final String baseUrl = "http://localhost:8080/customers/view/1" ;
		
			ResponseEntity<Customer> customerEntity = restTemplate.getForEntity(baseUrl, Customer.class);
			assertEquals(customerEntity.getStatusCode(), HttpStatus.OK);
			//assertThrows(RuntimeException.class, () -> restTemplate.getForEntity("http://localhost:8080/customers/view/{a}", Customer.class));
			assertNotNull(customerEntity.getBody());
		}
		
		@Test
	@Disabled
		void testPostRequest() {
			TestRestTemplate restTemplate = new TestRestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("customer", customer);
			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map , headers);
			ResponseEntity<Customer> responseEntity = restTemplate.postForEntity("http://localhost:8080/customers/add", request, Customer.class);
			assertNotNull(responseEntity.getBody());
			
		}
		@Test
		@Disabled
		void testPutRequest() {
			Customer customer1 = new Customer();
			customer1.setName("Name update");
			customer1.setAge(9);
			customer1.setDob(new Date());
			TestRestTemplate restTemplate = new TestRestTemplate();
			ResponseEntity<Customer> customerResponse = restTemplate.exchange("http://localhost:8080/customers/update/1", HttpMethod.PUT, new HttpEntity<>(customer1), Customer.class);
//			assertNotNull(customerResponse.getBody());
			assertEquals(customerResponse.getStatusCode(), HttpStatus.OK);
		}
		
		@Test
		@Disabled
		void testExpectedExceptionFail() {
		 
			NumberFormatException thrown = assertThrows(NumberFormatException.class, () -> {
							Integer.parseInt("1a");
						}, "NumberFormatException error was expected");
			
			assertEquals("1", thrown.getMessage());
		}
}
