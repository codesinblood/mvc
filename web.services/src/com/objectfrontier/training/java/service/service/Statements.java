package com.objectfrontier.training.java.service.service;

public interface Statements {

    String CREATE_PERSON = new StringBuilder()
            .append(" INSERT INTO  service_person(fname    ")
            .append("                           , lname     ")
            .append("                           , email         ")
            .append("                           , address_id    ")
            .append("                           , birth_date    ")
            .append("                           , created_date) ")
            .append(" VALUES (?, ?, ?, ?, ?,?)                  ")
            .toString();


    String READ_PERSON = new StringBuilder()
            .append(" SELECT id             ")
            .append("      , fname     ")
            .append("      , lname      ")
            .append("      , email          ")
            .append("      , address_id     ")
            .append("      , birth_date     ")
            .append("      , created_date   ")
            .append("   FROM service_person ")
            .append("  WHERE id = ?         ")
            .toString();

    String DELETE_PERSON = new StringBuilder()
            .append(" DELETE                ")
            .append("   FROM service_person ")
            .append("  WHERE id = ?         ")
            .toString();

    String UPDATE_QUERY = new StringBuilder()
            .append(" UPDATE service_person ")
            .append("    SET fname = ? ")
            .append("      , lname = ?  ")
            .append("      , email = ?      ")
            .append("      , birth_date = ? ")
            .append("  WHERE id = ?         ")
            .toString();

    String READ_ALL_PERSON = new StringBuilder()
            .append("SELECT     *                                          ")
            .append("  FROM service_address                                ")
            .append("  JOIN service_person                                 ")
            .append("    ON service_address.id = service_person.address_id ").toString();


    String DELETE_ADDRESS = new StringBuilder()
            .append(" DELETE                 ")
            .append("   FROM service_address ")
            .append("  WHERE id = ?          ")
            .toString();


    String UPDATE_ADDRESS = new StringBuilder()
            .append(" UPDATE service_address ")
            .append("    SET street = ?      ")
            .append("      , city = ?        ")
            .append("      , postal_code = ? ")
            .append("  WHERE id = ?          ")
            .toString();


    String READ_ALL_ADDRESS = new StringBuilder()
            .append(" SELECT id              ")
            .append("      , street          ")
            .append("      , city            ")
            .append("      , postal_code     ")
            .append("   FROM address_service ")
            .toString();

    String READ_ADDRESS = new StringBuilder()
            .append("SELECT        *        ")
            .append("  FROM service_address ")
            .append(" WHERE       id =     ?").toString();


    String CREATE_ADDRESS = new StringBuilder()
            .append(" INSERT INTO service_address(street       ")
            .append("                           , city         ")
            .append("                           , postal_code) ")
            .append(" VALUES(?,?,?)                            ")
            .toString();

}




/*public interface Statements {

    String READ_PERSON = new StringBuilder()
                            .append("SELECT id, fname, lname, email, address_id, birth_date, created_date FROM service_person ")
                            .append(" WHERE  service_person.id = ?").toString();

    String CREATE_PERSON = new StringBuilder()
                              .append("INSERT INTO service_person( `fname`, `lname`, `email`, address_id, birth_date, created_date) ")
                              .append("VALUES(?, ?, ?, ?, ?, ?) ").toString();

    String READ_ALL_PERSON = new StringBuilder()
                                .append("SELECT * FROM service_address ")
                                .append("  JOIN service_person ")
                                .append("    ON service_address.id = service_person.address_id ").toString();

    String UPDATE_QUERY = new StringBuilder()
                                .append("UPDATE service_person SET fname = ?, lname = ?, email = ?, birth_date = ? ")
                                .append("WHERE id = ?").toString();

    String DELETE_PERSON = new StringBuilder()
                            .append("DELETE FROM service_person ")
                            .append("WHERE id = ?").toString();

    String CREATE_ADDRESS = new StringBuilder()
                            .append("INSERT INTO service_address( `street`, `city`, `postal_code` )")
                            .append("VALUES (?, ?, ?)").toString();

    String UPDATE_ADDRESS = new StringBuilder()
                                  .append("UPDATE service_address SET street = ?, city = ?, postal_code = ? ")
                                  .append("WHERE id = ?").toString();

    String READ_ADDRESS = new StringBuilder()
                     .append("SELECT * FROM service_address ")
                     .append("WHERE id = ?").toString();

    String DELETE_ADDRESS = new StringBuilder()
                                .append("DELETE FROM service_address ")
                                .append("WHERE id = ?").toString();

}
*/