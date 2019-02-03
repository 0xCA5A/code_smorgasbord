package ch.casa;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// How to test access:
// mysql -udb_user -pdb_user_secret -h10.0.50.42 -P7000 -e 'show global variables like "max_connections"';


public class MariaDb {
    private static final Logger LOGGER = Logger.getLogger(MariaDb.class.getName());

    // Connection related information
    private static final String SERVER = "10.0.50.42";
    private static final int PORT = 7000;
    private static final String DB_NAME = "test_data";

    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = String.format("jdbc:mariadb://%s:%d/%s", SERVER, PORT, DB_NAME);

    //  Database credentials
    private static final String USER = "db_user";
    private static final String PASS = "db_user_secret";

    public static void main(String[] args) {

        // Register JDBC driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Open a connection
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            LOGGER.info("Connected database successfully...");

            List<Person> persons = new ArrayList<>();
            List<Long> accessDuration = new ArrayList<>();
            for (int i = 0; i < 3_000; i++) {
                long startTime = System.nanoTime();
                Person person = getRandomPerson(connection);
                long endTime = System.nanoTime();

                accessDuration.add((endTime - startTime) / 1000_000); // ms
                persons.add(person);
            }

            for (Person person : persons) {
                LOGGER.info(person.toString());
            }

            OptionalLong min = accessDuration.stream().mapToLong(x -> x).min();
            OptionalLong max = accessDuration.stream().mapToLong(x -> x).max();
            OptionalDouble average = accessDuration.stream().mapToLong(x -> x).average();

            LOGGER.info(String.format("Min DB access time: %s ms", min.isPresent() ? min.getAsLong() : "NaN"));
            LOGGER.info(String.format("Max DB access time: %s ms", max.isPresent() ? max.getAsLong() : "NaN"));
            LOGGER.info(String.format("Average DB access time: %s ms", average.isPresent() ? average.getAsDouble() : "NaN"));

        } catch (SQLException se) {
            throw new RuntimeException(se);
        }

        LOGGER.info("Goodbye!");
    }


    private static Person getRandomPerson(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String firstnameQuery = "SELECT firstname, sex FROM firstnames ORDER BY RAND() LIMIT 1;";
        ResultSet resultSet = stmt.executeQuery(firstnameQuery);
        resultSet.next();
        String firstname = resultSet.getString("firstname");
        String sex = resultSet.getString("sex");
        resultSet.close();

        String surnameQuery = "SELECT surname FROM surnames ORDER BY RAND() LIMIT 1;";
        resultSet = stmt.executeQuery(surnameQuery);
        resultSet.next();
        String surname = resultSet.getString("surname");
        resultSet.close();

        return new Person(firstname, surname, sex);
    }
}