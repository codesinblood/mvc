package com.objectfrontier.training.hibernate.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.objectfrontier.training.hibernate.model.Person;
import com.objectfrontier.training.hibernate.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value ="/person/readall")
    public String readAllPerson() throws Exception {

        List<Person> person = personService.readAll();
        return "Hi";
    }


    @GetMapping(value="person/read{id}")
    public Person readPerson ( @PathVariable("id") long id) throws Exception {

        Person readPerson = personService.read(id);
        return readPerson;
    }

    @PostMapping(value="person/delete/{personID}/{addressID}")
    public int deletePerson ( @PathVariable("personID") long personID, @PathVariable("addressID") long addressID ) throws Exception {

        int rowsAffected = personService.deletePerson(personID, addressID);
        return rowsAffected;
    }

    @PostMapping(value="person/create")
    public int createPerson (@RequestBody Person person) throws Exception {

        person.setCreated_date(Timestamp.from(Instant.now()));
        int createdID = personService.create(person);
        return createdID;
    }

    @PostMapping(value="person/update")
    public Person updatePerson (@RequestBody Person person) throws Exception {

        Person updatedPerson = personService.update(person);
        return updatedPerson;
    }

}
