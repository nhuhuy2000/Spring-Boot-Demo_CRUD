package com.example.demo.example;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControlerIntegrationTest {

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
	@MockBean
	private static CustomerRepository customerRepository;
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
	@BeforeEach
	public void save() {
		customerRepository.save(customer);
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
	public void getListCustomer_BodyNotNull_ListCustomer()throws Exception{//Success
		
		final String baseUrl = "http://localhost:8080/customers";
		//when
		ResponseEntity<Customer[]> listCusomerEntity = testRestTemplate.getForEntity(baseUrl, Customer[].class);
		//then
		assertEquals(listCusomerEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(listCusomerEntity);
		assertEquals(listCusomerEntity.getStatusCodeValue(), 200);
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
	public void givenNewCustomer_whenPostCustomer_thenReturns201() throws Exception {
	
		//given
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("customer", customer);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map , headers);
		//when
		ResponseEntity<Customer> responseEntity = testRestTemplate.postForEntity("http://localhost:8080/customers/add", request, Customer.class);
		//then
		assertNotNull(responseEntity.getBody());
//		assertEquals(responseEntity.getStatusCodeValue(), 201);
		
	}
	@Test
	@Disabled
	public void postListCustomer_ResponseMustHaveValue() throws Exception {
		
		//given
		List<Customer> list = new ArrayList<>();
		list.add(customer);
		list.add(new Customer(2, "Huy2", new Date(), 2));
		list.add(new Customer(3, "Huy3", new Date(), 3));
		final String baseUrl = "http://localhost:8080/customers/saveall";
		//when
		ResponseEntity<Customer[]> listCusomerEntity = testRestTemplate.postForEntity(baseUrl,list, Customer[].class);
		//then
		assertAll("tests", 
				() -> assertNotNull(listCusomerEntity.getBody()),
				() -> assertEquals(listCusomerEntity.getStatusCodeValue(), HttpStatus.OK.value())
		);
	}
	@Test

	public void updateCustomer_GoodInput_thenReturns201() throws Exception {
		//given
		Customer customer1 = new Customer();
		customer1.setName("Name update");
		customer1.setAge(9);
		customer1.setDob(new Date());
		
		//when
		ResponseEntity<Customer> customerResponse = testRestTemplate.exchange("http://localhost:8080/customers/update/1", HttpMethod.PUT, new HttpEntity<>(customer1), Customer.class);
		//then
		//			assertNotNull(customerResponse.getBody());
		assertEquals(customerResponse.getStatusCode(), HttpStatus.CREATED);
//		assertNotNull(customerResponse.getBody());
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
