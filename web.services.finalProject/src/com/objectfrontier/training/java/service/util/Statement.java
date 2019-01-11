package com.objectfrontier.training.java.service.util;

public interface Statement {

    String CREATE_PERSON = new StringBuilder()
            .append(" INSERT INTO  person_service(fname          ")
            .append("                           , lname          ")
            .append("                           , email          ")
            .append("                           , birth_date     ")
            .append("                           , password      ")
            .append(" VALUES (?, ?, ?, ?, ?,?, ?, ?)             ")
            .toString();

    String LOGIN_VALIDATION = new StringBuilder()
            .append("SELECT isAdmin         ")
            .append("  FROM person_service  ")
            .append(" WHERE email = ?       ")
            .append("   AND password = ?    ").toString();

    String READ_PERSON = new StringBuilder()
            .append(" SELECT id             ")
            .append("      , fname     ")
            .append("      , lname      ")
            .append("      , email          ")
            .append("      , birth_date     ")
            .append("   FROM person_service ")
            .append("  WHERE id = ?         ")
            .toString();

    String DELETE_PERSON = new StringBuilder()
            .append(" DELETE                ")
            .append("   FROM person_service ")
            .append("  WHERE id = ?         ")
            .toString();

    String UPDATE_QUERY = new StringBuilder()
            .append(" UPDATE person_service ")
            .append("    SET fname = ? ")
            .append("      , lname = ?  ")
            .append("      , email = ?      ")
            .append("      , birth_date = ? ")
            .append("  WHERE id = ?         ")
            .toString();

    String READ_ALL_PERSON = new StringBuilder()
            .append("SELECT     *                                          ")
            .append("  FROM person_service                             ").toString();


    String DELETE_ADDRESS = new StringBuilder()
            .append(" DELETE                 ")
            .append("   FROM address_service ")
            .append("  WHERE id = ?          ")
            .toString();


    String UPDATE_ADDRESS = new StringBuilder()
            .append(" UPDATE address_service ")
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
            .append("  FROM address_service ")
            .append(" WHERE       id =     ?").toString();


    String CREATE_ADDRESS = new StringBuilder()
            .append(" INSERT INTO address_service(street       ")
            .append("                           , city         ")
            .append("                           , postal_code) ")
            .append(" VALUES(?,?,?)                            ")
            .toString();

}




/*public interface Statements {

    String READ_PERSON = new StringBuilder()
                            .append("SELECT id, fname, lname, email, address_id, birth_date, created_date FROM person_service ")
                            .append(" WHERE  person_service.id = ?").toString();

    String CREATE_PERSON = new StringBuilder()
                              .append("INSERT INTO person_service( `fname`, `lname`, `email`, address_id, birth_date, created_date) ")
                              .append("VALUES(?, ?, ?, ?, ?, ?) ").toString();

    String READ_ALL_PERSON = new StringBuilder()
                                .append("SELECT * FROM address_service ")
                                .append("  JOIN person_service ")
                                .append("    ON address_service.id = person_service.address_id ").toString();

    String UPDATE_QUERY = new StringBuilder()
                                .append("UPDATE person_service SET fname = ?, lname = ?, email = ?, birth_date = ? ")
                                .append("WHERE id = ?").toString();

    String DELETE_PERSON = new StringBuilder()
                            .append("DELETE FROM person_service ")
                            .append("WHERE id = ?").toString();

    String CREATE_ADDRESS = new StringBuilder()
                            .append("INSERT INTO address_service( `street`, `city`, `postal_code` )")
                            .append("VALUES (?, ?, ?)").toString();

    String UPDATE_ADDRESS = new StringBuilder()
                                  .append("UPDATE address_service SET street = ?, city = ?, postal_code = ? ")
                                  .append("WHERE id = ?").toString();

    String READ_ADDRESS = new StringBuilder()
                     .append("SELECT * FROM address_service ")
                     .append("WHERE id = ?").toString();

    String DELETE_ADDRESS = new StringBuilder()
                                .append("DELETE FROM address]_service ")
                                .append("WHERE id = ?").toString();

}
*/