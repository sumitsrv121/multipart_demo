package com.sumit.multipartdemo.dao;

import com.sumit.multipartdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
