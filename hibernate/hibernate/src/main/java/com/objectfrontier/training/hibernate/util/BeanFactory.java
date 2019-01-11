package com.objectfrontier.training.hibernate.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.objectfrontier.training.hibernate.model.Address;
import com.objectfrontier.training.hibernate.service.AddressService;
import com.objectfrontier.training.hibernate.service.PersonService;



public class BeanFactory {

    static ApplicationContext context = new ClassPathXmlApplicationContext("/config.xml");

    public static <T> T createBean(Class<T> type) {

        return context.getBean(type);
    }

    public static AddressService getAddressService () {
        return context.getBean("addressService" , AddressService.class);
    }

    public static Address getAddress () {
        return context.getBean("address" , Address.class);
    }

    public static PersonService getPersonService () {
        return context.getBean("personService" , PersonService.class);
    }
}
