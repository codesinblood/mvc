package com.objectfrontier.training.java.service.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.util.HttpMethod;
import com.objectfrontier.training.java.service.util.JsonConverter;
import com.objectfrontier.training.java.service.util.RequestHelper;

public class AuthTest extends TestBaseServlet{

    RequestHelper helper;


    @Test(dataProvider = "dp_AuthPositive")
    public void test_AuthPositive(Person input) throws Exception {

        helper = new RequestHelper();
        helper.setBaseUrl("http://localhost:8080/ws.ccc/");
        helper.setMethod(HttpMethod.POST);

        Person actual = helper.setSecured(true)
                              .requestObject("/login" + input.getId(), Person.class);

        Assert.assertEquals(JsonConverter.toJson(actual),JsonConverter.toJson(input));


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
