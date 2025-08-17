package com.java.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.java.springboot.model.Employee;
import com.java.springboot.repository.EmployeeRepository;

@SpringBootApplication
public class SpringbootBackendApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void run(String... args) throws Exception {
		Employee employee = new Employee();
		employee.setFirstName("Khafia");
		employee.setLastName("Ayyub");
		employee.setEmailId("khafiaayyub@gmail.com");
		employeeRepository.save(employee);
		
		Employee employee1 = new Employee();
		employee1.setFirstName("Cutu");
		employee1.setLastName("Himanshu");
		employee1.setEmailId("hitman@gmail.com");
		employeeRepository.save(employee1);
		
		
		
		
	}
	


	
}
