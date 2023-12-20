package com.rk.Assignment.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.rk.Assignment.dto.EmployeeDto;


public interface EmployeeService {

	String createEmp(EmployeeDto employeeDto);

	EmployeeDto updateEmployee(EmployeeDto employeeDto, String empId);

	void deleteEmployee(String empId);

	List<EmployeeDto> getAllEmployees();

	EmployeeDto getEmployeeById(String empId);

	String EmployeeNthLevelManger(String empId, Integer level);

	List<EmployeeDto> getAllPost(Integer pageNumber, Integer pageSize, String sortBy);

}
