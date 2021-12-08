package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.service.ServiceInterface;


@RestController
public class CustomerController {
	
//		@RequestMapping("/")
//		@ResponseBody
//		public String test() {
//			return "sasddfd";
//		}
		@Autowired
		private ServiceInterface customerService;
		
		@RequestMapping(value = "/customers", method=RequestMethod.GET)
		public ResponseEntity<Iterable<Customer>> getCustomer() {
			return new ResponseEntity<>(customerService.listCustomers(), HttpStatus.OK);
		}
		@RequestMapping(value = "/customers/add", method=RequestMethod.POST)
		public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
			return new ResponseEntity<>(customerService.save(customer), HttpStatus.OK);
		}
		@RequestMapping(value = "/customers/view/{id}", method=RequestMethod.GET)
		public ResponseEntity<Customer> viewCustomer(@PathVariable String id) {
			Optional<Customer> getCustomer = customerService.findById(Integer.parseInt(id));
			return getCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
		}
		@RequestMapping(value = "/customers/update/{id}", method=RequestMethod.PUT)
		public Optional<Customer> updateCustomer(@PathVariable("id") String id ,  @Valid @RequestBody Customer customer) {
			return customerService.updateCustomer(Integer.parseInt(id), customer);
		}
		@RequestMapping(value = "/customers/delete/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Customer> deleteCustomer(Customer customer) {
			customerService.delete(customer);
			return ResponseEntity.noContent().build();
		}
}
