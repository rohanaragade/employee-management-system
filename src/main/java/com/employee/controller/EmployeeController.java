package com.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.entity.Employee;
import com.employee.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	// Add Employee
	@PostMapping("/saveEmployee")
	public Employee saveEmployee(@RequestBody Employee employee) {
		return service.saveEmp(employee);
	}

	// Get All Employees
	@GetMapping("/getEmployees")
	public List<Employee> getAllEmployees() {
		return service.getAllEmp();
	}

	// Get Employee By Id
	@GetMapping("/getEmployee/{id}")
	public Optional<Employee> getEmployeeById(@PathVariable int id) {
		return service.getEmpById(id);
	}

	// Delete Employee
	@DeleteMapping("/deleteEmployee/{id}")
	public void deleteEmployee(@PathVariable int id) {

		service.deleteEmpId(id);

	}

	// Update Employee
	@PutMapping("/updateEmployee/{id}")
	public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employeeData) {

		Employee employee = service. getEmpById(id).orElseThrow();

        employee.setName(employeeData.getName());
        employee.setDepartment(employeeData.getDepartment());
        employee.setSalary(employeeData.getSalary());

        return service.saveEmp(employee);
	}
}
