package com.objectfrontier.training.java.service.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.util.AppException;
import com.objectfrontier.training.java.service.util.ConnectionManager;
import com.objectfrontier.training.java.service.util.ErrorCode;
import com.objectfrontier.training.java.service.util.HttpMethod;
import com.objectfrontier.training.java.service.util.JsonConverter;
import com.objectfrontier.training.java.service.util.RequestHelper;


@Test
public class PersonServletTest {

    public RequestHelper helper;

    @Test(dataProvider = "dp_ReadPositiveCase",enabled = false)
    public void testRead_positive(Person test, Person expectedPerson) throws SQLException {

        try {

            helper = new RequestHelper();
            helper.setBaseUrl("http://localhost:8080/ws/");
            helper.setMethod(HttpMethod.GET);

            Person actual = helper.setSecured(false)
                                  .requestObject("read?id=" + test.getId(), Person.class);

            Assert.assertEquals(JsonConverter.toJson(actual),JsonConverter.toJson(expectedPerson));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] dp_ReadPositiveCase() {

        Person test = new Person();
        Person expected = new Person();
        test.setId(38);
        test.setFirstName("ed");
        expected = test;

        return new Object[][] {
            { test, expected }
        };
    }

    @Test(dataProvider = "dp_ReadNegativeCase", enabled = false)
    public void testRead_Negative(long id, Person expectedPerson, String error) throws Exception {

        Connection connection = ConnectionManager.createConnection().getConnection();
        Person actual = new Person();
        try {

            helper = new RequestHelper();
            helper.setBaseUrl("http://localhost:8080/ws/");
            helper.setMethod(HttpMethod.GET);
            actual = helper.setSecured(false).requestObject("read?id=" + id, Person.class);
        } catch(Exception e) {
            Assert.assertEquals(e.getMessage(), error);
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] dp_ReadNegativeCase() {

        Person p = new Person();
        Person q = new Person();
        p.setId(8);
        q = p;

        String expected = "Person not found";
        return new Object[][] {
            { p.getId(), q, expected }
        };
    }

    @Test(dataProvider = "dp_CreatePositive", enabled = true)
    private void testCreate_positive(Person input, Person expectedPerson) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws");
            helper = new RequestHelper();

            Person createdPerson = helper.setSecured(false)
                    .setMethod(HttpMethod.PUT)
                    .setInput(input)
                    .requestObject("/person/create", Person.class);

            Assert.assertEquals(JsonConverter.toJson(createdPerson), JsonConverter.toJson(expectedPerson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] dp_CreatePositive() {


        Address a = new Address("Sardinia", "Sanhok", 600055);
        Person inputPerson = new Person("Elana","Unknown","elana@pubg",a, "06-06-1996", "password1", true);

        Person expectedPerson = inputPerson;
        expectedPerson.setId(76);
        return new Object[][] {
            { inputPerson, expectedPerson }
        };
    }

    @Test(dataProvider = "dp_CreateNegative", enabled = false)
    private void testCreate_Negative(Person input, List<ErrorCode> expectedError) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws");
            helper = new RequestHelper();

            Person createdPerson = helper.setSecured(false)
                    .setMethod(HttpMethod.PUT)
                    .setInput(input)
                    .requestObject("/person/create", Person.class);


        } catch (Exception e) {
            if(e instanceof AppException) {
                System.out.println(((AppException) e).getErrorList());
                Assert.assertEquals(((AppException) e).getErrorList(), expectedError);
            }
        }
    }

    @DataProvider
    public Object[][] dp_CreateNegative() {


        Address a = new Address("", "", 600055);
        Person inputPerson = new Person("hari","","nirav@karthik",a,"06-06-1985");
        Person expectedPerson = inputPerson;
        expectedPerson.setId(76);
        List<ErrorCode> expected = new ArrayList<>();
        expected.add(ErrorCode.CITY_NOT_NULL);
        expected.add(ErrorCode.STREET_NOT_NULL);
        expected.add(ErrorCode.LAST_NAME_NOT_NULL);

        return new Object[][] {
            { inputPerson,  expected }
        };
    }

    @Test(dataProvider = "dp_UpdatePositive", enabled = false)
    private void testUpdate_positive(Person input, Person expectedPerson) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws");
            helper = new RequestHelper();

            Person createdPerson = helper.setSecured(false)
                                    .setMethod(HttpMethod.POST)
                                    .setInput(input)
                                    .requestObject("/person/update", Person.class);

            Assert.assertEquals(JsonConverter.toJson(createdPerson), JsonConverter.toJson(expectedPerson));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] dp_UpdatePositive() {

        Address a = new Address(380,"Tbm", "Chennai", 600055);
        Person inputPerson = new Person(74,"Sabari","Raj","praveenhari@shash",a,"06-06-1955");
        Person expectedPerson = inputPerson;
        return new Object[][] {
            { inputPerson, expectedPerson }
        };
    }

    @Test(dataProvider = "dp_UpdateNegative", enabled = false)
    private void testUpdate_Negative(Person input, List<ErrorCode> expectedError) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws");
            helper = new RequestHelper();

            Person createdPerson = helper.setSecured(false)
                                    .setMethod(HttpMethod.POST)
                                    .setInput(input)
                                    .requestObject("/person/update", Person.class);


        } catch (Exception e) {
            if(e instanceof AppException) {
                System.out.println(((AppException) e).getErrorList());
                Assert.assertEquals(((AppException) e).getErrorList(), expectedError);
            }
        }
    }

    @DataProvider
    public Object[][] dp_UpdateNegative() {

        Address a = new Address(380,"Tbm", "Chennai", 600055);
        Person inputPerson = new Person(74,"","Raj","praveenhari@shash",a,"06-06-1955");
        List<ErrorCode> expected = new ArrayList<>();
        expected.add(ErrorCode.LAST_NAME_NOT_NULL);
        return new Object[][] {
            { inputPerson, expected }
        };
    }

    @Test(dataProvider = "dp_DeleteNegative", enabled = false)
    private void testDelete_Negative(Person input, Person expectedPerson, List<ErrorCode> expectedError) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws");
            helper = new RequestHelper();

            int createdPerson = helper.setSecured(false)
                                    .setMethod(HttpMethod.POST)
                                    .setInput(input)
                                    .requestObject("/person/update?id=" + input.getId(), Integer.class);

        } catch (Exception e) {
            Assert.assertEquals(((AppException) e).getErrorList(),expectedError);
        }
    }


    @Test(dataProvider = "dp_DeletePositive", enabled = false)
    private void testDelete_positive(Person input, Person expectedPerson) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws");
            helper = new RequestHelper();

            int createdPerson = helper.setSecured(false)
                                    .setMethod(HttpMethod.POST)
                                    .setInput(input)
                                    .requestObject("/person/update?id=" + input.getId(), Integer.class);

            Assert.assertEquals(createdPerson,1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] dp_DeleteNegative() {

        Address inputAddress = new Address(5000,"chen","chn",676454);
        Person inputPerson = new Person(5000,"chen","chn","jhsbad@asd",inputAddress, "06-06-1996");
        inputPerson.setAddress(inputAddress);
        List<ErrorCode> expectedList = new ArrayList<>();
        expectedList.add(ErrorCode.PERSON_NOT_FOUND);
        Person expectedPerson = inputPerson;

        return new Object[][] {
            { inputPerson, expectedPerson, expectedList }
        };
    }

    @DataProvider
    public Object[][] dp_DeletePositive() {

        Address a = new Address(380,"Tbm", "Chennai", 600055);
        Person inputPerson = new Person(74,"vijay","siva","praveen@karthik",a,"06-06-1955");
        Person expectedPerson = inputPerson;
        return new Object[][] {
            { inputPerson, expectedPerson }
        };
    }
}
