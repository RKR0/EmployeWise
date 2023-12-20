package com.rk.Assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.rk.Assignment.dto.EmployeeDto;
import com.rk.Assignment.service.EmployeeService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
      this.employeeService = employeeService;
  }
	

	// Add a new Employee
	@PostMapping("/add")
	public ResponseEntity createEmp(@Valid  @RequestBody EmployeeDto employeeDto){
		
		try {
			String createEmpdto = employeeService.createEmp(employeeDto);
			return new ResponseEntity(createEmpdto,HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	// Update Exciting Employee 
	@PutMapping("empId/{empId}")
	public ResponseEntity updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable String empId){
		
		try {
			EmployeeDto updateEmp = employeeService.updateEmployee(employeeDto, empId);
			return new ResponseEntity(updateEmp,HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	// Delete Employee
	@DeleteMapping("empId/{empId}")
	public ResponseEntity deleteEmployee(@PathVariable String empId){
		
		try {
			employeeService.deleteEmployee(empId);
			return new ResponseEntity("Employee Deleted Sucessfully!!",HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	// get all Employees
	@GetMapping("/all/")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
		
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}
	
	
	
	// get Employee by Id
	@GetMapping("empId/{empId}")
	public ResponseEntity getEmployeeById(@PathVariable String empId){
		
		try {
			EmployeeDto emp = employeeService.getEmployeeById(empId);
			return new ResponseEntity(emp,HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	// get Employee by Id
	@GetMapping("empId/{empId}/level/{level}")
	public ResponseEntity EmployeeNthLevelManger(@PathVariable String empId,@PathVariable Integer level){
		
		try {
			String msg = employeeService.EmployeeNthLevelManger(empId,level);
			return new ResponseEntity(msg,HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/")
	public ResponseEntity getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = "10", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "firstName", required = false) String sortBy)
 {

		List<EmployeeDto> employees = this.employeeService.getAllPost(pageNumber, pageSize, sortBy);
		return new ResponseEntity(employees, HttpStatus.OK);
	}
	
}
