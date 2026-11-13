package com.example.EMPLOYEE_MANAGEMENT_RESTapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.EMPLOYEE_MANAGEMENT_RESTapi.model.Employee;

/**
 * Business logic for employees.
 *
 * Employees are stored in memory (a Map), so no database is needed.
 * The data resets every time the application restarts.
 */
@Service
public class EmployeeService {

    private final Map<Long, Employee> employees = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    // Two sample employees so the API has data as soon as it starts
    public EmployeeService() {
        addEmployee(new Employee(null, "Chhavi Rani", "Engineering", 75000));
        addEmployee(new Employee(null, "Rahul Sharma", "HR", 50000));
    }

    // Add a new employee with an auto-generated ID
    public Employee addEmployee(Employee employee) {
        Long newId = idGenerator.incrementAndGet();
        employee.setId(newId);
        employees.put(newId, employee);
        return employee;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    // Get one employee (empty if the ID does not exist)
    public Optional<Employee> getEmployeeById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    // Update an employee (empty if the ID does not exist)
    public Optional<Employee> updateEmployee(Long id, Employee details) {
        Employee existing = employees.get(id);
        if (existing == null) {
            return Optional.empty();
        }
        existing.setName(details.getName());
        existing.setDepartment(details.getDepartment());
        existing.setSalary(details.getSalary());
        return Optional.of(existing);
    }

    // Delete an employee (true if deleted, false if the ID does not exist)
    public boolean deleteEmployee(Long id) {
        return employees.remove(id) != null;
    }
}
