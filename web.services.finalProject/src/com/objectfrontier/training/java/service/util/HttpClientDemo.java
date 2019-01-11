package com.objectfrontier.training.java.service.util;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.pojo.Person;

public class HttpClientDemo {

    private void run(String[] args) throws Exception {

        String url = "http://localhost:8080/ws/";

        RequestHelper.setBaseUrl(url);
        RequestHelper helper = new RequestHelper();
        helper.setMethod(HttpMethod.POST);
        Person p = new Person();
        p.setId(48);
        Address ad = new Address();
        ad.setId(349);

        int actual = helper.setSecured(false)
                              .requestObject("/person/update", Integer.class);

    }

    public static void main(String[] args) {
        try {
            new HttpClientDemo().run(args);
        } catch (Exception e) {
            log(e);
        }
    }

    private static void log(Object o) {
        if (o instanceof Throwable) {
            ((Throwable)o).printStackTrace(System.err);
        } else {
            System.out.println(o);
        }
    }
}