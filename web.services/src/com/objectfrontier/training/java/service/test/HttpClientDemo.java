package com.objectfrontier.training.java.service.test;

public class HttpClientDemo {

    private void run(String[] args) throws Exception {

        String url = "http://localhost:8080/ws/";

        RequestHelper.setBaseUrl(url);
        RequestHelper helper = new RequestHelper();
        helper.setMethod(HttpMethod.POST);

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