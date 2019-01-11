package hibernate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.objectfrontier.training.hibernate.model.Address;
import com.objectfrontier.training.hibernate.model.Person;
import com.objectfrontier.training.hibernate.service.PersonService;

public class ServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private DataSource dataSource;

    @Mock
    private Session session;

    public Person person;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        Address address = new Address( "taramani street",
                                       "chennai",
                                       600234);
        person = new Person( "Sriram",
                            "narayanan",
                            "sriram@gmail.com",
                            address,
                            "1996-12-06",
                            "0221",
                            true);
        person.setId(1);
    }

    @Test
    public void createTest() throws Exception {

    PersonService mockDemoClass = mock(PersonService.class);
    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.save(person)).thenReturn(3);
    int expected = personService.create( person);
    System.out.println(personService.getClass());
    System.out.println(mockDemoClass.getClass());
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
