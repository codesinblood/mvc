package com.objectfrontier.training.java.service.test;


import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.util.AppException;
import com.objectfrontier.training.java.service.util.ErrorCode;
import com.objectfrontier.training.java.service.util.HttpMethod;
import com.objectfrontier.training.java.service.util.RequestHelper;

public class TestAddressServlet {

    private static RequestHelper helper;

    @BeforeClass
    private void authenticate() {
        RequestHelper.setBaseUrl("http://localhost:8080/ws.ccc");
    }

    @Test(dataProvider = "dpCreate", priority = 1, enabled = true)
    public void testCreate(Address input, AppException expectedError) {

        List<Address> addrList = new ArrayList<>();

        try {

            Address result = new RequestHelper().setSecured(true)
                                   .setMethod(HttpMethod.PUT)
                                   .setInput(input)
                                   .requestObject("/do/address?ops=create", Address.class);
            System.out.println(result.toString());
            Assert.assertEquals(result , input);
            addrList.add(result);
//            Assert.assertEquals(JsonConverter.toJson(result), JsonConverter.toJson(input));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            if(e instanceof AppException) {
                Assert.assertEquals(((AppException) e).getErrorList(), expectedError.getErrorList());
            } else {
                Assert.fail();
            }
        }
    }

    @DataProvider
    public Object[][] dpCreate() {
        return new Object[][] {
            new Object[] { new Address(2, "OMR",  null, 600013), new AppException(ErrorCode.CITY_NOT_NULL) },
//            new Object[] { new Address(3, "ECR", "Chennai", 600013), new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3) },
//            new Object[] { new Address(4, "CSIR", "Chennai", 600013), new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3) }
        };
    }
}