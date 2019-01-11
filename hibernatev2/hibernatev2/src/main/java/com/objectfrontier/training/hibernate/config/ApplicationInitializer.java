package com.objectfrontier.training.hibernate.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class < ? > [] getRootConfigClasses() {
        return new Class[] {
                ApplicationConfig.class
        };
    }
    @Override
    protected Class < ? > [] getServletConfigClasses() {
        return new Class[] {
                WebConfiguration.class
        };
    }
    @Override
    protected String[] getServletMappings() {
        return new String[] {
                "/"
        };
    }
}