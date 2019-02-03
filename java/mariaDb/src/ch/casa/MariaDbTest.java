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


public class MariaDbTest {
    private static final Logger logger = Logger.getLogger(MariaDbTest.class.getName());

    private static final String jdbcDriver = "org.mariadb.jdbc.Driver";

    private final String dbUrl;

    MariaDbTest(String dbName, String server, int port) {
        this.dbUrl = String.format("jdbc:mariadb://%s:%d/%s", server, port, dbName);
    }

    void start(String username, String password) {

        // Register JDBC driver
        registerJDBCDriver();

        // Open a connection
        try (Connection connection = DriverManager.getConnection(this.dbUrl, username, password)) {
            logger.info("Connected database successfully...");

            createTables(connection);

            List<String> tableNames = new ArrayList<>();
            tableNames.add("firstnames");
            tableNames.add("surnames");
            initTablesFromLocalFile(connection, tableNames);

            final List<Person> randomPersons = createRandomPersons(connection, 42);
            insertCustomers(connection, randomPersons);


        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    private List<Person> createRandomPersons(Connection connection, int numberOfPersons) throws SQLException {
        logger.info(String.format("Create %d random persons", numberOfPersons));
        List<Person> persons = new ArrayList<>();
        List<Long> accessDuration = new ArrayList<>();
        for (int i = 0; i < numberOfPersons; i++) {
            final long startTime = System.nanoTime();
            Person person = createRandomPerson(connection);
            final long endTime = System.nanoTime();

            accessDuration.add((endTime - startTime) / 1000_000); // ms
            persons.add(person);
        }

        OptionalLong min = accessDuration.stream()
                .mapToLong(x -> x)
                .min();
        OptionalLong max = accessDuration.stream()
                .mapToLong(x -> x)
                .max();
        OptionalDouble average = accessDuration.stream()
                .mapToLong(x -> x)
                .average();

        logger.fine(String.format("Min DB access time: %s ms", min.isPresent() ? min.getAsLong() : "NaN"));
        logger.fine(String.format("Max DB access time: %s ms", max.isPresent() ? max.getAsLong() : "NaN"));
        logger.fine(String.format("Average DB access time: %s ms", average.isPresent() ? String.format("%.3f", average.getAsDouble()) : "NaN"));

        return persons;
    }

    private void registerJDBCDriver() {
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        // Connection related information
        final String SERVER = "10.0.50.42";
        final int PORT = 7000;
        final String DB_NAME = "test_data";

        // Database credentials
        final String USERNAME = "db_user";
        final String PASSWORD = "db_user_secret";

        MariaDbTest mariaDbTest = new MariaDbTest(DB_NAME, SERVER, PORT);
        mariaDbTest.start(USERNAME, PASSWORD);

        logger.info("Goodbye!");
    }

    private static void insertCustomers(Connection connection, List<Person> persons) throws SQLException {
        for (Person person : persons) {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format("INSERT INTO customer VALUES (Null, '%s', '%s')", person.firstName, person.surName);
                statement.executeUpdate(sql);
            }
        }
    }

    private static void initTablesFromLocalFile(Connection connection, List<String> tableNames) throws SQLException {
        for (String tableName : tableNames) {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format("TRUNCATE TABLE %s", tableName);
                statement.execute(sql);

                String fileName = String.format("data/%s.txt", tableName);
                logger.info(String.format("Init table '%s' from local file '%s'", tableName, fileName));
                sql = String.format("LOAD DATA LOCAL INFILE '%s' INTO TABLE %s;", fileName, tableName);
                statement.execute(sql);
            }
        }
    }

    private static void createTables(Connection connection) throws SQLException {

        List<String> tablesToCreate = new ArrayList<>();

        String surnamesSql =
                "CREATE TABLE IF NOT EXISTS surnames (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  surname varchar(50) NOT NULL," +
                        "  PRIMARY KEY (id)" +
                        ");";
        tablesToCreate.add(surnamesSql);

        String firstnamesSql =
                "CREATE TABLE IF NOT EXISTS firstnames (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  firstname varchar(50) NOT NULL," +
                        "  gender varchar(1) NOT NULL," +
                        "  PRIMARY KEY (id)" +
                        ");";
        tablesToCreate.add(firstnamesSql);

        String customerSql =
                "CREATE TABLE IF NOT EXISTS customer (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  firstname varchar(50) NOT NULL," +
                        "  lastname varchar(50) NOT NULL," +
                        "  PRIMARY KEY (id)" +
                        ");";
        tablesToCreate.add(customerSql);

        String contactSql =
                "CREATE TABLE IF NOT EXISTS contact (" +
                        "  id INT," +
                        "  customer_id INT," +
                        "  info varchar(50) NOT NULL," +
                        "  type varchar(50) NOT NULL," +
                        "  INDEX par_ind (customer_id)," +
                        "  CONSTRAINT fk_customer FOREIGN KEY (customer_id)" +
                        "  REFERENCES customer2(id)" +
                        "  ON DELETE CASCADE" +
                        "  ON UPDATE CASCADE" +
                        ");";
        tablesToCreate.add(contactSql);

        for (String tableCreateSql : tablesToCreate) {
            String tableName = getTableNameByCreateStatement(tableCreateSql);
            logger.info(String.format("Create table '%s' (if not existing)", tableName));
            try (Statement statement = connection.createStatement()) {
                statement.execute(tableCreateSql);
            }
        }
    }

    private static String getTableNameByCreateStatement(String tableCreateSql) {
        String[] tableNameTokens = tableCreateSql.split("\\(");
        tableNameTokens = tableNameTokens[0].trim().split(" ");
        return tableNameTokens[tableNameTokens.length - 1];
    }

    private static Person createRandomPerson(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String firstnameQuery = "SELECT firstname, gender FROM firstnames ORDER BY RAND() LIMIT 1;";
        ResultSet resultSet = statement.executeQuery(firstnameQuery);
        resultSet.next();
        String firstname = resultSet.getString("firstname");
        String gender = resultSet.getString("gender");
        resultSet.close();

        String surnameQuery = "SELECT surname FROM surnames ORDER BY RAND() LIMIT 1;";
        resultSet = statement.executeQuery(surnameQuery);
        resultSet.next();
        String surname = resultSet.getString("surname");
        resultSet.close();

        final Person person = new Person(firstname, surname, gender);
        logger.fine(String.format("New random Person created: %s", person));
        return person;
    }
}