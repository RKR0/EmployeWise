package com.rk.Assignment.transformer;

import java.time.LocalDateTime;

import com.rk.Assignment.dto.EmployeeDto;
import com.rk.Assignment.model.Employee;

public class EmployeeTransformer {

	// Convert EmployeeDto Class to Employee Class
    public static Employee EmployeeDtoToEmployee(EmployeeDto employeeDto){

        return Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .phoneNumber(employeeDto.getPhoneNumber())
                .profileImage(employeeDto.getProfileImage())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    // Convert Employee Class to EmployeeDto Class
    public static EmployeeDto EmployeeToEmployeeDto(Employee employee){

        return EmployeeDto.builder()
        		.id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .profileImage(employee.getProfileImage())
                .reportingId(employee.getReportingId())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}

