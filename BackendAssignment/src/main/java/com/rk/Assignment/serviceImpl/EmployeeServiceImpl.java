package com.rk.Assignment.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.rk.Assignment.dto.EmployeeDto;
import com.rk.Assignment.exception.EmployeeNotFoundException;
import com.rk.Assignment.model.Employee;
import com.rk.Assignment.repository.EmployeeRepo;
import com.rk.Assignment.service.EmployeeService;
import com.rk.Assignment.transformer.EmployeeTransformer;



@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	JavaMailSender javaMailSender;
	
	final EmployeeRepo employeeRepo;
	
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepo employeeRepo){
		this.employeeRepo = employeeRepo;
	}
	
	

	@Override
	public String createEmp(EmployeeDto employeeDto) {
		
		Employee e = EmployeeTransformer.EmployeeDtoToEmployee(employeeDto);
		

        if(employeeDto.getReportingId()!=null) {
        	
            Optional<Employee> reporting = employeeRepo.findById(employeeDto.getReportingId());
    		
            if(reporting.isEmpty()){
                throw new EmployeeNotFoundException("Invalid Reporting Id Number!!!");
            }
            e.setReportingId(employeeDto.getReportingId());
        }
        
		e.setId(UUID.randomUUID().toString());		
		Employee emp = employeeRepo.save(e);
		
	      //  send an email
		 if(employeeDto.getReportingId()!=null) {
			 
			 Optional<Employee> reporting = employeeRepo.findById(employeeDto.getReportingId());
			 
			 
        String text = " Dear " + reporting.get().getFirstName() + 
        		" \n\n We are pleased to inform you that a new employee has been added to your team." +
                " \n\n\n Name: " + emp.getFirstName() + 
                " \n Email: "+emp.getEmail() +
                " \n Phone Number: "+emp.getPhoneNumber() +

                " \n\n "+emp.getFirstName() +" will now be working under your supervision. Please take the necessary steps to onboard and integrate them into the team."+
                
                "\n\n\n Best regards,"+
                "\n Hr Team.";
                

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("kanna51198@gmail.com");
        simpleMailMessage.setTo(reporting.get().getEmail());
        simpleMailMessage.setSubject("New Employee Assignment");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
}
		return emp.getId();
	}

	@Override
	public EmployeeDto updateEmployee(EmployeeDto employeeDto, String empId) {
		
		Optional<Employee> employee = employeeRepo.findById(empId);
		
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("Invalid Employee Id Number!!!");
        }

        Employee e = EmployeeTransformer.EmployeeDtoToEmployee(employeeDto);
        e.setId(empId);
        e.setCreatedAt(employee.get().getCreatedAt());
        
        
        if(employeeDto.getReportingId()!=null) {
        	
            Optional<Employee> reporting = employeeRepo.findById(employeeDto.getReportingId());
    		
            if(reporting.isEmpty()){
                throw new EmployeeNotFoundException("Invalid Reporting Id Number!!!");
            }
            e.setReportingId(employeeDto.getReportingId());
        }

		Employee emp = employeeRepo.save(e);
		
		return EmployeeTransformer.EmployeeToEmployeeDto(emp);
	}

	// delete Employee by using id
	@Override
	public void deleteEmployee(String empId) {
		
		Optional<Employee> employee = employeeRepo.findById(empId);
		
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("Invalid Employee Id Number!!!");
        }
        
        employeeRepo.deleteById(empId);
		
	}

	
	// get all employees
	@Override
	public List<EmployeeDto> getAllEmployees() {
		
		List<Employee> employees = employeeRepo.findAll();
		
		List<EmployeeDto> ans = new ArrayList<>();
		
		for(Employee e:employees) {
			
			EmployeeDto Emp = EmployeeTransformer.EmployeeToEmployeeDto(e);
			
			ans.add(Emp);
		}
		
		return ans;
	}

	// fetch Employee by using id
	@Override
	public EmployeeDto getEmployeeById(String empId) {
		
		Optional<Employee> employee = employeeRepo.findById(empId);
		
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("Invalid Employee Id Number!!!");
        }
        
        return EmployeeTransformer.EmployeeToEmployeeDto(employee.get());
	}





	// Employee nth Level Reporting Manager
	@Override
	public String EmployeeNthLevelManger(String empId, Integer level) {

		
		String s[] = helper(empId,level,empId);
		
		Optional<Employee> employee2 = employeeRepo.findById(s[0]);
		Optional<Employee> employee1 = employeeRepo.findById(s[1]);
		
		return "Manager "+employee2.get().getFirstName()+" for Employee "+employee1.get().getFirstName();
	}


	private String[] helper(String empId, Integer level, String empId2) {
		
		if(level==0) {
			String s[] = {empId2,empId};
			return s;
			
		}
			
		
		Optional<Employee> employee = employeeRepo.findById(empId2);
		
        if(!employee.isEmpty()){
            return helper(empId,level-1,employee.get().getReportingId());
        }
        
        String s[] = {empId2,empId};
		return s;
	}



	@Override
	public List<EmployeeDto> getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {
		
		Pageable p = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		
		Page<Employee> employees = employeeRepo.findAll(p);
		
		List<EmployeeDto> ans = new ArrayList<>();
		
		for(Employee e:employees) {
			
			EmployeeDto Emp = EmployeeTransformer.EmployeeToEmployeeDto(e);
			
			ans.add(Emp);
		}
		
		return ans;
		
	}
	
	
	

}
