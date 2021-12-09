package com.example.demo.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

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

import com.example.demo.repository.CustomerRepository;








@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerControllerIntegrationTest {
		
		@Autowired
		private CustomerRepository customerRepository;
		@Autowired
		private TestRestTemplate restTemplate;
		static Customer customer;
		@BeforeAll
		public void setup() {
			customer = new Customer();
			customer.setId(1);
			customer.setName("HUY");
			customer.setAge(1);
			customer.setDob(new Date());
			customerRepository.save(customer);
		}
//		@BeforeEach
//		public void save() {
//			
//		}
//		@AfterEach
//		public void tearDown() {
//			customerRepository.deleteAll();
//		}
		
		@Test
		@Order(1)
		public void postListCustomer_GoodInput_HttpStatusOK() throws Exception {//SUCCESS
			
			//given
			List<Customer> list = new ArrayList<>();
			list.add(customer);
			list.add(new Customer(2, "Huy2", new Date(), 2));
			list.add(new Customer(3, "Huy3", new Date(), 3));
			final String baseUrl = "http://localhost:8080/customers/saveall";
			//when
			ResponseEntity<Customer[]> listCusomerEntity = restTemplate.postForEntity(baseUrl,list, Customer[].class);
			//then
			assertAll("tests", 
				() -> assertNotNull(listCusomerEntity.getBody()),
					() -> assertEquals(listCusomerEntity.getStatusCodeValue(), HttpStatus.OK.value())
			);
			assertEquals(3, listCusomerEntity.getBody().length);
		}
		@Test
		@Order(2)
		public void postNewCustomer_GoodInput_HttpStatusCREATE() throws Exception {//SUCCESS
		
			//given
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("customer", customer);
			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map , headers);
			//when
			ResponseEntity<Customer> responseEntity = restTemplate.postForEntity("http://localhost:8080/customers/add", request, Customer.class);
			//then
			assertNotNull(responseEntity.getBody());
			assertEquals(responseEntity.getStatusCodeValue(), 201);
			assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
			assertEquals(null, responseEntity.getBody().getName());
			
		}
		
		@Test
		@Order(3)
		public void getCustomer_CorrectPath_Customer() throws Exception  {//SUCCESS
			//when
		
			ResponseEntity<Customer> customerEntity = restTemplate.getForEntity("http://localhost:8080/customers/view/1", Customer.class);
			
			//then
			assertEquals(customerEntity.getStatusCode(), HttpStatus.OK);
		
			assertNotNull(customerEntity.getBody().getId());
			assertEquals("HUY", customerEntity.getBody().getName());
		}
		@Test		
		@Order(4)
		public void getListCustomer_CorrectPath_ListCustomers()throws Exception{//SUCCESS
			
			final String baseUrl = "http://localhost:8080/customers";
			//when
			ResponseEntity<Customer[]> listCusomerEntity = restTemplate.getForEntity(baseUrl, Customer[].class);
			//then
			assertEquals(listCusomerEntity.getStatusCode(), HttpStatus.OK);
			assertNotNull(listCusomerEntity);
			assertEquals(listCusomerEntity.getStatusCodeValue(), 200);
			assertEquals(1, listCusomerEntity.getBody().length);
		}
		
		
		
		
		@Test
		@Order(5)
		public void updateCustomer_GoodInput_HttpStatusCREATE() throws Exception {//SUCCESS
			//given
			Customer customer1 = new Customer();
			customer1.setName("Name update");
			customer1.setAge(9);
			customer1.setDob(new Date());
			TestRestTemplate restTemplate = new TestRestTemplate();
			//when
			ResponseEntity<Customer> customerResponse = restTemplate.exchange("http://localhost:8080/customers/update/1", HttpMethod.PUT, new HttpEntity<>(customer1), Customer.class);
			//then
			assertNotNull(customerResponse.getBody());
			assertEquals(customerResponse.getStatusCode(), HttpStatus.CREATED);

		}
			
		
		@Test
		@Order(6)
		public void testExceptionThrown_ErrorURL_AssertionSucceed() {
			assertThrows(RuntimeException.class, () -> restTemplate.getForEntity("http://localhost:8080/customers/view/{a}", Customer.class));
		}
		@Test
		@Order(7)
		public void testFindCustomerNotExistByName_TypeValueString_EqualToInput() {//SUCCESS
			String input = "ABC";
			Customer customer = customerRepository.findByName(input);
			assertNull(customer);
			
		}
		@Test
		@Order(8)
		public void testFindCustomerById_TypeValueInteger_Customer() {//SUCCESS
			int id = 1;
			Optional<Customer> customer = customerRepository.findById(id);
			assertNotNull(customer);
			
		}
}
