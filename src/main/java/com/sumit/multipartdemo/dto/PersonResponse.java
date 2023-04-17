package com.sumit.multipartdemo.dto;

import com.sumit.multipartdemo.entity.Employee;
import com.sumit.multipartdemo.entity.Student;

import java.util.List;

public class PersonResponse {
    private List<Employee> employees;
    private List<Student> students;


    public PersonResponse() {
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
