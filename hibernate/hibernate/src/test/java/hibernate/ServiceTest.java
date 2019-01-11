package hibernate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

import com.objectfrontier.training.hibernate.model.Person;
import com.objectfrontier.training.hibernate.service.PersonService;

public class ServiceTest {

    @Test
    public void createTest() throws Exception {

    PersonService mockDemoClass = mock(PersonService.class);
    Person person = new Person("sriram", "karthick", "sriram@gmail.com", null, "19-09-1997");
    when(mockDemoClass.create(person)).thenReturn(3);
    Serializable expected = mockDemoClass.create( person);
    Assert.assertEquals(3, expected);
    }

    @Test
    public void readTest() throws Exception {

        PersonService mockDemoClass = mock(PersonService.class);
        Person person = new Person("sriram", "ram", "sriram@gmail.com", null, "19-09-1997");
        when(mockDemoClass.read( 1)).thenReturn(person);
        Person expected = mockDemoClass.read( 1);
        Person actual = person;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void updateTest() throws Exception {

        PersonService mockDemoClass = mock(PersonService.class);
        Person person = new Person("sriram", "ram", "sriram@gmail.com", null, "19-09-1997");
        when(mockDemoClass.update( person)).thenReturn(person);
        Person expected = mockDemoClass.update( person);
        Person actual = person;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void deleteTest() throws Exception {

        PersonService mockDemoClass = mock(PersonService.class);
        Person person = new Person("sriram", "ram", "sriram@gmail.com", null, "19-09-1997");
        when(mockDemoClass.deletePerson( 1, 1)).thenReturn(1);
        int expected = mockDemoClass.deletePerson( 1, 1);
        int actual = 1;
        Assert.assertEquals(actual, expected);
    }
}
