package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.engine.query.spi.sql.NativeSQLQueryCollectionReturn;
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
			return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
		}
		@RequestMapping(value = "/customers/view/{id}", method=RequestMethod.GET)
		public ResponseEntity<Customer> viewCustomer(@PathVariable int id) {
			Optional<Customer> getCustomer = customerService.findById(id);
			if(getCustomer.isPresent()) {
				return new ResponseEntity<>(getCustomer.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		@RequestMapping(value = "/customers/update/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Optional<Customer>> updateCustomer(@PathVariable("id") String id ,  @Valid @RequestBody Customer customer) {
			return new ResponseEntity<>(customerService.updateCustomer(Integer.parseInt(id), customer) , HttpStatus.CREATED);
		}
		@RequestMapping(value = "/customers/delete/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Customer> deleteCustomer(Customer customer) {
			customerService.delete(customer);
			return ResponseEntity.noContent().build();
		}
		@RequestMapping(value = "customers/saveall", method=RequestMethod.POST)
		public ResponseEntity<Iterable<Customer>> postListCustomer(@RequestBody List<Customer> list){
			return new ResponseEntity<>(customerService.saveAll(list), HttpStatus.OK);
}
}