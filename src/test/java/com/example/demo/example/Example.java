package com.example.demo.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class Example {
	static int a;
	static int b;
	
	@BeforeAll
	public static void setup() {
		a = 5 ;
		b = 6;
	}
	class Calculator {
		int sum(int a , int b) {
			return a+b;
		}
	}
	@BeforeEach
	public void reset() {
		a = 3;
	}
	Calculator calculator = new Calculator();
	@Test
	public void toTal() {
		//when
		int result = calculator.sum(a, b);
		
		//then 
		int expected = 11;
		assertThat(result).isEqualTo(expected);
	}
}
