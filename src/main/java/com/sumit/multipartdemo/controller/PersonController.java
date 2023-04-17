package com.sumit.multipartdemo.controller;

import com.sumit.multipartdemo.dto.Person;
import com.sumit.multipartdemo.dto.PersonResponse;
import com.sumit.multipartdemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping
    public PersonResponse savePersons(@RequestBody List<Person> persons) {
        return personService.savePersons(persons);
    }
}
