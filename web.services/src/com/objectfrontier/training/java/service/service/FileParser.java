package com.objectfrontier.training.java.service.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.pojo.Person;

public class FileParser {

    public List<Person> readFile() throws IOException {

        Path filePath = Paths.get("resource/sp.csv");
        Stream <String> myStream = Files.lines(filePath);
        List<String[]> myList = myStream.map(action -> action.split(",")).collect(Collectors.toList());
        List<Person> createPerson = new ArrayList<>();
        for (String[] strings : myList) {

            Address address = new Address(strings[5], strings[6], Integer.valueOf(strings[7]));
            createPerson.add(new Person(strings[0], strings[1], strings[2], address, strings[4]));
        }
        myStream.close();
        return createPerson;
    }

public Object[][] dataPool() throws IOException {

        FileParser file = new FileParser();
        List<Person> personList = file.readFile();
        List<Person> expectedList = personList;
        Person actualPerson = new Person();
        Person expectedPerson = new Person();

        Object[][] person =  new Object[personList.size()][2];
        for(int i = 0; i < personList.size(); i++) {
            person [i][0] = personList.get(i);
            person [i][1] = expectedList.get(i);
        }

        return person;
        }

}
