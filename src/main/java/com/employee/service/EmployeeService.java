package com.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository empRepo;
	
	public List<Employee> getAllEmp(){
		return empRepo.findAll();
	}
	
	public Optional<Employee> getEmpById(int id){
		return Optional.ofNullable(empRepo.findById(id).orElseThrow());
	}
	
	public Employee saveEmp(Employee employee) {
		return empRepo.save(employee);
	}
	
	public void deleteEmpId(int id) {
		empRepo.deleteById(id);
	}
}
