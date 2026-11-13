package com.example.EMPLOYEE_MANAGEMENT_RESTapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMPLOYEE_MANAGEMENT_RESTapi.model.Employee;
import com.example.EMPLOYEE_MANAGEMENT_RESTapi.service.EmployeeService;

/**
 * REST controller for the Employee Management System.
 *
 * Base URL: http://localhost:8080/api/employees
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Constructor injection: Spring passes in the EmployeeService automatically
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * (b) Add a new employee.
     * POST /api/employees  -> 201 Created
     */
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * (c) Get all employees.
     * GET /api/employees  -> 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * (c) Get a specific employee by ID.
     * GET /api/employees/{id}  -> 200 OK, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        }
        return notFound(id);
    }

    /**
     * (d) Update an employee's details.
     * PUT /api/employees/{id}  -> 200 OK, or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> updated = employeeService.updateEmployee(id, employee);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return notFound(id);
    }

    /**
     * (e) Delete an employee by ID.
     * DELETE /api/employees/{id}  -> 200 OK with a message, or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.ok(Map.of("message", "Employee with id " + id + " deleted successfully"));
        }
        return notFound(id);
    }

    // Common 404 Not Found response used by GET, PUT and DELETE
    private ResponseEntity<Object> notFound(Long id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Employee not found with id: " + id));
    }
}
