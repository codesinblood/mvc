package com.objectfrontier.training.java.service.test;

import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.util.AppException;
import com.objectfrontier.training.java.service.util.ErrorCode;
import com.objectfrontier.training.java.service.util.HttpMethod;
import com.objectfrontier.training.java.service.util.RequestHelper;


public class TestBaseServlet {

    Person person = new Person("niknejad@yahoo.com", "password3");

    protected RequestHelper login() { return login(person); }
    protected RequestHelper login(Person person) {

        try {

            RequestHelper helper = RequestHelper.create();
            HttpResponse response = helper.setMethod(HttpMethod.POST).setInput(person).requestRaw("/do/login");
            log(RequestHelper.asString(response));
            Assert.assertEquals(RequestHelper.getStatus(response), 200);
            helper.setSecureDetails(response);
            return helper;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L1);
        }
    }

    private static Server server;
    private static int port = 8080;
    private static String contextPath = "/ws.ccc";

    @BeforeSuite
    protected void initServer() throws Exception {

        server = new Server(port);

        URL webXmlResource = server.getClass().getClassLoader().getResource("web.xml");
        URI webResourceBase = webXmlResource.toURI().resolve("..").normalize();

        log("Using BaseResource: " + webResourceBase);
        WebAppContext context = new WebAppContext();
        context.setBaseResource(Resource.newResource(webResourceBase));

        context.setContextPath(contextPath);
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        server.start();

        String baseUrl = String.format("http://localhost:%s%s", port, contextPath);
        RequestHelper.setBaseUrl(baseUrl);
    }

    @AfterSuite
    protected void stopServer() throws Exception {
        server.stop();
    }

    void log(String message) {
        System.out.println(message);
    }
 }


