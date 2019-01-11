package com.objectfrontier.training.java.service.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.service.PersonService;
import com.objectfrontier.training.java.service.util.ConnectionManager;
import com.zaxxer.hikari.HikariDataSource;


public class PersonServiceTest {

    private PersonService serv;
    private Connection connection;
    private Connection con;
    private HikariDataSource hikariDS;

    @BeforeClass
    private void init() throws Exception {

        serv = new PersonService();
//        connection = ConnectionManager.dbAccess();
        hikariDS = ConnectionManager.createConnection();

    }

    @Test(dataProvider= "dp_Create_Positive", enabled = true)
    private void testCreate_positive(Person person, Person expectedPerson) throws Exception {

        try {
            con = hikariDS.getConnection();
            con.setAutoCommit(false);
            System.out.println(System.currentTimeMillis());
            System.out.println(con);
            Person actualPerson = serv.create(con, person);
            Assert.assertEquals(actualPerson.getEmail(), expectedPerson.getEmail());
            con.commit();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "dp_CreatePersonNegative", enabled = false)
    public void testCreate_Negative(Person testPerson, String errorMsg) throws Exception {

        Connection conn = null;
        try {
            conn = hikariDS.getConnection();
            conn.setAutoCommit(false);
            System.out.println(con);
            Person actualPerson = serv.create(conn, testPerson);
            conn.commit();
        }
        catch(Exception e) {
            conn.rollback();
            conn.close();
            Assert.assertEquals(e.getMessage(), errorMsg);
        }
    }

    @Test(dataProvider = "dp_Update_Positive", enabled = false)

    private void testUpdate_positive(Person updatePerson, Person expectedPerson) throws Exception {

        try {

            connection = ConnectionManager.createConnection().getConnection();
            Person expected = serv.update(connection, updatePerson);
            Assert.assertEquals(expected, expectedPerson);
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test(dataProvider = "dp_Delete_Positive", enabled = false)
    private void testDelete_positive(Long person_id, Long address_id) throws Exception {

        try {
            int actualRowCount = serv.deletePerson(connection, person_id, address_id);
            int expectedRowCount = 1;
            Assert.assertEquals(actualRowCount, expectedRowCount);
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Test(dataProvider = "dp_ReadPositiveCase", enabled = false)
    private void testRead_positive(Person actualPerson, Person expPerson) throws Exception {

        con = hikariDS.getConnection();
        System.out.println(con);
        Person actPerson = serv.read(con, actualPerson.getId(), "n");
        System.out.println(actPerson);
        Assert.assertEquals(actPerson.getId(), expPerson.getId());
        con.close();
    }

    @Test(dataProvider = "dp_ReadNegativeCase", enabled = false)
    private void testRead_negative(Person testPerson, Person expPerson, String expectedResult) throws SQLException {

        try {
            Person actPerson = serv.read(connection, testPerson.getId(), "n");
        } catch (Exception e){
            Assert.assertEquals(e.getMessage(), expectedResult);
        }
    }

    @Test(dataProvider = "dp_UpdateNegativeCase", enabled = false)
    public void testUpdate_Negative1(Person testPerson, String error) throws SQLException {

        try {
            Person actualPerson = serv.update(connection, testPerson);
        } catch(Exception e) {
            Assert.assertEquals(e.getMessage(), error);
        }
    }

    @Test(dataProvider = "dp_Delete_Negative", enabled = false)
    public void testDelete_Negative(Long person_id, Long address_id, String error) throws Exception {

        try {
            con = hikariDS.getConnection();
            int affectedRows = serv.deletePerson(con, person_id, address_id);
        } catch(Exception e) {
            Assert.assertEquals(e.getMessage(), error);
        } finally {
            con.close();
        }
    }

    @DataProvider(parallel = false)
    public Object[][] dp_ReadPositiveCase() throws SQLException, IOException {

        PersonServiceTest parseFile = new PersonServiceTest();
        Path filePath = Paths.get("resource//read.csv");
        Person p = new Person();
        Person q = new Person();
        p.setId(38);
        q = p;
//        Object[][] obj = parseFile.readPersonParser(filePath);
//        Object[][] obj1 =;
//        return obj;

        return new Object[][] {
            {p,q}
        };
    }

    @DataProvider
    Object[][] dp_Update_Positive() throws SQLException {

        Address addAddress = new Address(341, "APC", "Madurai", 614514);
        Person addPerson = new Person(43, "Siva", "Kavi", "741852963@gmail.com", addAddress, "06-05-1996");

        Person expectedPerson = addPerson;
        return new Object[][] {
            { addPerson, expectedPerson }
        };
    }

    @DataProvider
    Object[][] dp_Delete_Positive() throws SQLException {

        Long person_id = 23L;
        Long address_id = 22L;
        return new Object[][] {
            { person_id, address_id}
        };
    }

    @DataProvider
    public Object[][] dp_ReadNegativeCase() throws SQLException {

        long id = 6;
        Person testPerson = new Person();
        testPerson.setId(id);
        Person expectedPerson = new Person();
        expectedPerson.setFirstName("Ramesh");
        String expected = "Person not found";
        return new Object[][] {
            {testPerson, expectedPerson, expected}
        };
    }


    @DataProvider
    public Object[][] dp_UpdateNegativeCase() throws SQLException {

        Address updateAddress = new Address(18, "APC", "Tuty", 614514);
        Person updatePerson = new Person(19, "", "Kumar", "gknm34@gmail.com", updateAddress, "06-05-1996");
        Person updatePerson2 = new Person(19, "Ram", "", "gknm22@gmail.com", updateAddress, "06-05-1996");
        Person updatePerson3 = new Person(19, "Raj", "Raj", "gknm45@gmail.com", updateAddress, "06-05-1996");
        Person updatePerson4 = new Person(19, "Siva", "Kumar", "", updateAddress, "06-05-1996");
        Address updateAddress2 = new Address("", "Tuty", 614514);
        Person updatePerson5 = new Person(19, "Siva", "Kumar", "gknm56@gmail.com", updateAddress2, "06-05-1996");
        Address updateAddress3 = new Address("APC", "", 614514);
        Person updatePerson6 = new Person(19, "Siva", "Kumar", "gknm67@gmail.com", updateAddress3, "06-05-1996");
        Address updateAddress4 = new Address(20, "APC", "Thirupathur", 000000);
//        Person updatePerson7 = new Person(21, "Siva", "Kumar", "gknm78@gmail.com", updateAddress4, "06-05-1996");
        Person updatePerson9 = new Person(19, "Siva", "Raj", "vk@gmail.com", updateAddress, "06-05-1996");
        String FIRST_NAME_NOT_NULL = "First Name cannot be empty";
        String NAME_NOT_SAME = "First Name and Last Name cannot be same";
        String LAST_NAME_NOT_NULL = "Last Name cannot be empty";
        String CITY_NOT_NULL = "City cannot be empty";
        String STREET_NOT_NULL = "Street cannot be empty";
        String EMAIL_NOT_NULL = "Email cannot be null";
        String PINCODE_NOT_NULL = "Pincode cannot be null";
        String PERSON_NOT_FOUND = "Person does not exist";
        String EMAIL_ALREADY_EXISTS = "Email already exists";
        String ID_NOT_FOUND = "Person not found";

        return new Object[][] {
            { updatePerson, FIRST_NAME_NOT_NULL },
            { updatePerson2, LAST_NAME_NOT_NULL },
            { updatePerson3, NAME_NOT_SAME },
            { updatePerson4, EMAIL_NOT_NULL },
            { updatePerson5, CITY_NOT_NULL },
            { updatePerson6, STREET_NOT_NULL },
//            { updatePerson7, PINCODE_NOT_NULL },
            { updatePerson9, ID_NOT_FOUND }
        };
    }

    @DataProvider
    public Object[][] dp_Delete_Negative() {

        Long person_id = 1000L;
        Long address_id = 1000L;
        String error = "Person does not exist";
        return new Object[][] {
            { person_id, address_id, error}
        };
    }

    @DataProvider(parallel = true)
    Object[][] dp_Create_Positive() throws SQLException, IOException {

        PersonServiceTest parseFile = new PersonServiceTest();
        Path filePath = Paths.get("res//sp.csv");
        Object[][] obj = parseFile.createPersonParser(filePath);
        return obj;
    }

    public Object[][] createPersonParser(Path filePath) throws IOException {

        Stream <String> myStream = Files.lines(filePath);
        List<String[]> myList = myStream.map(action -> action.split(",")).collect(Collectors.toList());
        List<Person> createPerson = new ArrayList<>();
        for (String[] strings : myList) {

            Address address = new Address(strings[5], strings[6], Integer.valueOf(strings[7]));
            createPerson.add(new Person(strings[0], strings[1], strings[2], address, strings[4], strings[8], Boolean.valueOf(strings[9])));
            Person readPerson = new Person();
        }

        List<Person> expectedList = createPerson;

        Object[][] person =  new Object[createPerson.size()][2];
        for(int i = 0; i < createPerson.size(); i++) {
            person [i][0] = createPerson.get(i);
            person [i][1] = expectedList.get(i);
        }

        return person;
    }


    @DataProvider(parallel = true)
    public Object[][] dp_CreatePersonNegative() throws IOException {

        PersonServiceTest parseFile = new PersonServiceTest();
        Path filePath = Paths.get("resource//create-ve.csv");
        Object[][] obj = parseFile.createPersonNegativeParser(filePath);
        return obj;
    }

    public Object[][] createPersonNegativeParser(Path filePath) throws IOException {

        Stream <String> myStream = Files.lines(filePath);
        List<String[]> myList = myStream.map(action -> action.split(",")).collect(Collectors.toList());
        List<Person> createPersonNegative = new ArrayList<>();
        List<String> errorList = new ArrayList<>();

        for (String[] strings : myList) {

            Address address = new Address(strings[5], strings[6], Integer.valueOf(strings[7]));
            createPersonNegative.add(new Person(strings[0], strings[1], strings[2], address, strings[4]));
        }

        for (String[] strings1 : myList) {

            String error = strings1[8];
            errorList.add(error);
        }

        Object[][] person =  new Object[createPersonNegative.size()][2];
        for(int i = 0; i < createPersonNegative.size(); i++) {
            person [i][0] = createPersonNegative.get(i);
            person [i][1] = errorList.get(i);
        }

        return person;
    }

    public Object[][] readPersonParser(Path filePath) throws IOException {

        Stream <String> myStream = Files.lines(filePath);
        List<String> myList = myStream.collect(Collectors.toList());
        List<Person> readPerson = new ArrayList<>();
        for (String strings : myList) {

            readPerson.add(new Person(Integer.parseInt(strings), null, null, null, null, null));
        }

        List<Person> expectedList = readPerson;

        Object[][] person =  new Object[readPerson.size()][2];
        for(int i = 0; i < readPerson.size(); i++) {
            person [i][0] = readPerson.get(i);
            person [i][1] = expectedList.get(i);
        }

        return person;
    }


//    @AfterClass
//     public void end() throws SQLException {
//
//        ConnectionManager.destroyConnection(connection);
//        ConnectionManager.destroyConnection(con);
//    }

}
