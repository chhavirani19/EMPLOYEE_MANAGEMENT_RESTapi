package com.example.EMPLOYEE_MANAGEMENT_RESTapi.model;

/**
 * (a) Employee model class.
 *
 * Each employee record contains: id, name, department and salary.
 * It has a no-argument constructor, a parameterized constructor,
 * and getters/setters for every field.
 */
public class Employee {

    private Long id;
    private String name;
    private String department;
    private double salary;

    // No-argument constructor (Spring uses it to convert JSON -> Employee)
    public Employee() {
    }

    // Parameterized constructor
    public Employee(Long id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // ----- Getters and Setters -----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', department='" + department
                + "', salary=" + salary + "}";
    }
}
