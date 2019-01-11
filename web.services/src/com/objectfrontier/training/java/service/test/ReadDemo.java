package com.objectfrontier.training.java.service.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.objectfrontier.training.java.service.pojo.Person;

public class ReadDemo {

    public static void main(String[] args) throws IOException {

        ReadDemo rd = new ReadDemo();
        Path filePath = Paths.get("resource//read.csv");
        rd.readPersonParser(filePath);
    }

    public Object[][] readPersonParser(Path filePath) throws IOException {

        Stream <String> myStream = Files.lines(filePath);
        List<String> myList = myStream.collect(Collectors.toList());
        List<Person> readPerson = new ArrayList<>();
        for (String strings : myList) {

            readPerson.add(new Person(Integer.parseInt(strings), null, null, null, null, null));
        }

        System.out.println(myList.get(3));

        List<Person> expectedList = readPerson;

        Object[][] person =  new Object[readPerson.size()][2];
        for(int i = 0; i < readPerson.size(); i++) {
            person [i][0] = readPerson.get(i);
            person [i][1] = expectedList.get(i);
        }

        return person;
    }

}
