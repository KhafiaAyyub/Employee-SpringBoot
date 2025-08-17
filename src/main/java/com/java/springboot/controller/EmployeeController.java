package com.java.springboot.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.springboot.exceptions.ResourceNotFoundException;
import com.java.springboot.model.Employee;
import com.java.springboot.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
    @GetMapping
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
	}
    
    //build create emloyee rest api
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee) ;
    	
    }
	
    //build get employee by id rest api
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
    	Employee employee = employeeRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+ id));
		return ResponseEntity.ok(employee);
    	
    }
    
    //build update employee rest api
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails){
    	Employee updateEmployee = employeeRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+ id));
    	updateEmployee.setFirstName(employeeDetails.getFirstName());
    	updateEmployee.setLastName(employeeDetails.getLastName());
    	updateEmployee.setEmailId(employeeDetails.getEmailId());
    	
    	employeeRepository.save(updateEmployee);
    	return ResponseEntity.ok(updateEmployee);
    	
    }
    
    //build delete employee rest api
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
    	Employee employee =  employeeRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+ id));
    	employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    @GetMapping("/paginationAndSorting")
    public ResponseEntity<List<Employee>> getEmployeesWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,       // page number (0-based)
            @RequestParam(defaultValue = "5") int size,       // page size
            @RequestParam(defaultValue = "id") String sortBy, // sorting field
            @RequestParam(defaultValue = "asc") String sortDir // asc or desc
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort); // ✅ correct import

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return ResponseEntity.ok(employeePage.getContent());
    }

    
}
