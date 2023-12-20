package com.rk.Assignment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rk.Assignment.model.Employee;


@Repository
public interface EmployeeRepo extends MongoRepository<Employee,String>{

	Page<Employee> findAll(Pageable pageable);
}
