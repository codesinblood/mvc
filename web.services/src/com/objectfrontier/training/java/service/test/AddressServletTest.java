package com.objectfrontier.training.java.service.test;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.service.pojo.Address;

@Test
public class AddressServletTest {

    public RequestHelper helper;

    @Test(dataProvider = "dp_ReadPositiveCase",enabled = false)
    public void testRead_positive(Address test, Address expectedPerson) throws SQLException {

        try {

            helper = new RequestHelper();
            helper.setBaseUrl("http://localhost:8080/ws/");
            helper.setMethod(HttpMethod.GET);

            Address actual = helper.setSecured(false)
                                  .requestObject("readAddress?id=" + test.getId(), Address.class);

            Assert.assertEquals(JsonConverter.toJson(actual),JsonConverter.toJson(expectedPerson));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] dp_ReadPositiveCase() {

        Address test = new Address();
        Address expected = new Address();
        test.setId(350);
        test.setCity("Valluvar Colony");
        test.setStreet("Tuty");
        test.setPostal_code(651);
        expected = test;

        return new Object[][] {
            { test, expected }
        };
    }

    @Test(dataProvider = "dp_CreatePositive", enabled = false)
    private void testCreate_positive(Address input, Address expectedAddress) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws");
            helper = new RequestHelper();

            Address createdAddress = helper.setSecured(false)
                    .setMethod(HttpMethod.PUT)
                    .setInput(input)
                    .requestObject("/address/create", Address.class);

            Assert.assertEquals(JsonConverter.toJson(createdAddress), JsonConverter.toJson(expectedAddress));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] dp_CreatePositive() {


        Address test = new Address(383, "Tbm", "Chennai", 600055);
        Address expected = test;
        return new Object[][] {
            { test, expected }
        };

    }

    @Test(dataProvider = "dp_UpdatePositive", enabled = true)
    private void testUpdate_positive(Address input) {

        try {
            RequestHelper.setBaseUrl("http://localhost:8080/ws");
            helper = new RequestHelper();

            Boolean createdAddress = helper.setSecured(false)
                    .setMethod(HttpMethod.POST)
                    .setInput(input)
                    .requestObject("/address/update", Boolean.class);

            Assert.assertEquals(JsonConverter.toJson(createdAddress), JsonConverter.toJson(true));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] dp_UpdatePositive() {

        Address test = new Address(350,"Theppakulam", "Thoothukudi", 641028);
        return new Object[][] {
            { test }
        };
    }

}
