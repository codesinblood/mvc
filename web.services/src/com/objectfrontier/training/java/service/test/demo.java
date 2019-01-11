package com.objectfrontier.training.java.service.test;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.pojo.Person;

public class demo {

    public static void main(String[] args) {

        Address a = new Address(50, "salem", "elampillai", 675675);
        Person p = new Person(50, "vijay", "siva", "vijay@siva" , a, "06-06-1996");
        System.out.println(JsonConverter.toJson(p));

    }

}
