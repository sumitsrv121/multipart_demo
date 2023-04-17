package com.sumit.multipartdemo.service;

import com.sumit.multipartdemo.dao.EmployeeRepository;
import com.sumit.multipartdemo.dao.StudentRepository;
import com.sumit.multipartdemo.dto.Person;
import com.sumit.multipartdemo.dto.PersonResponse;
import com.sumit.multipartdemo.entity.Employee;
import com.sumit.multipartdemo.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
public class PersonService {
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;

    public PersonService(EmployeeRepository employeeRepository, StudentRepository studentRepository) {
        this.employeeRepository = employeeRepository;
        this.studentRepository = studentRepository;
    }

    public PersonResponse savePersons(List<Person> persons) {
        long start = System.currentTimeMillis();
        List<Employee> employees = persons.stream().filter((person) -> {
            return person.getName().length() % 2 == 0;
        }).map(person -> new Employee(person.getName())).collect(Collectors.toList());
        System.out.println("Employee: " + employees.size());

        List<Student> students = persons.stream().filter((person) -> {
            return person.getName().length() % 2 != 0;
        }).map(person -> new Student(person.getName())).collect(Collectors.toList());
        System.out.println("Student: " + students.size());

        CompletableFuture<List<Student>> saveStudentCf = saveStudents(students);
        CompletableFuture<List<Employee>> saveEmployeeCf = saveEmployees(employees);
        PersonResponse response = CompletableFuture.allOf(saveStudentCf, saveEmployeeCf)
                .thenApply(ignored -> {
                    List<Student> studentFinalList = saveStudentCf.join();
                    List<Employee> employeeFinalList = saveEmployeeCf.join();

                    PersonResponse personResponse = new PersonResponse();
                    personResponse.setEmployees(employeeFinalList);
                    personResponse.setStudents(studentFinalList);
                    return personResponse;
                }).join();


        long end = System.currentTimeMillis();
        System.out.println("persons saving took: " + (end - start));
        return response;
    }

    @Transactional
    public CompletableFuture<List<Employee>> saveEmployees(final List<Employee> employees) {
        return CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            List<Employee> employeeList = new LinkedList<>();
            try {
                for (Employee employee : employees) {
                    try {
                        if ("sumit".equalsIgnoreCase(employee.getName())) {
                            throw new RuntimeException("Hero is found");
                        }
                        employeeList.add(employeeRepository.save(employee));
                    } catch (Exception e) {
                        employeeList.add(new Employee(employee.getName()));
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            long end = System.currentTimeMillis();
            System.out.println("employees saving took: " + (end - start));
            return employeeList;
        });
    }

    @Transactional
    public CompletableFuture<List<Student>> saveStudents(final List<Student> students) {
        return CompletableFuture.supplyAsync(() -> {
                    long start = System.currentTimeMillis();
                    List<Student> savedStudentsList = studentRepository.saveAll(students);
                    long end = System.currentTimeMillis();
                    System.out.println("students saving took: " + (end - start));

                    return savedStudentsList;
                })
                .exceptionally(e -> null);
    }
}
