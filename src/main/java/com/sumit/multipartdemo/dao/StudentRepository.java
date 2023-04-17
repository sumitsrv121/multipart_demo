package com.sumit.multipartdemo.dao;

import com.sumit.multipartdemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
