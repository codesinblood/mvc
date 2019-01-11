package com.objectfrontier.training.java.service.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.util.HttpMethod;
import com.objectfrontier.training.java.service.util.RequestHelper;


public class ServletTest {

    RequestHelper helper;

    @Test(dataProvider = "dp_AuthPositive", enabled = true)
    private void testAuth_positive(Person input) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws.ccc");
            helper = new RequestHelper();

            Person createdPerson = helper.setSecured(false)
                    .setMethod(HttpMethod.POST)
                    .setInput(input)
                    .requestObject("/auth", Person.class);

            Assert.assertEquals(createdPerson, true);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        }
    }

    @DataProvider

    public Object[][] dp_AuthPositive() {

        Person person = new Person();
        person.setEmail("niknejad@yahoo.com");
        person.setPassword("password3");

        return new Object[][] {
            { person }
        };
    }
}
