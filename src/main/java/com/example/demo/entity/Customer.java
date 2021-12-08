package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity

@Table(name = "customer")
public class Customer implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message = "Name is required")
	private String name;
	public Customer(int id, @NotNull(message = "Name is required") String name,
			@NotNull(message = "Age is required") int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	@NotNull(message = "Dob is required")
	private Date dob;
	@NotNull(message = "Age is required")
	private int age;
	public Customer(int id, @NotNull(message = "Name is required") String name,
			@NotNull(message = "Dob is required") Date dob, @NotNull(message = "Age is required") int age) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.age = age;
	}
	public Customer(int id, @NotNull(message = "Name is required") String name,
			@NotNull(message = "Dob is required") Date dob,
			@NotNull(message = "Creditlimit is required") Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.age = age;
	}
	public Customer( @NotNull(message = "Name is required") String name,
			@NotNull(message = "Dob is required") Date dob,
			@NotNull(message = "Creditlimit is required") Integer age) {
		super();
		
		this.name = name;
		this.dob = dob;
		this.age = age;
	}
	public Customer() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	
	
}
