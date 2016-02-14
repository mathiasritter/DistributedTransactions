package at.mritter.dezsys06.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * Creates a new connection to a mysql database.
 *
 * @author Mathias Ritter
 */
public class MySQLConnectionCreator extends DBConnectionCreator {

    private static Logger logger = LogManager.getLogger(MySQLConnectionCreator.class.getName());
    private Connection connection;

    /**
     * Load driver for mysql
     */
    public MySQLConnectionCreator() {
        try {
            //Laden des MySQL-Treibers
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //Falls nicht gefunden, Fehlermeldung ausgeben und Programm verlassen
            logger.error("Driver for MySQL not found.");
            logger.error(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * @see DBConnectionCreator#getConnection()
     */
    public Connection getConnection() {

        if (connection == null)
            this.createConnection();

        return this.connection;
    }

    /**
     * Creates a new database connection with autocommit set to false.
     */
    private void createConnection() {

        //Connection-String speziell fuer MySQL
        String connectionString = "jdbc:mysql://" + super.getHost() + "/" + super.getDatabase() + "?useSSL=false";
        try {
            //Neue Connection mittels Driver-Manager initialisieren
            this.connection = DriverManager.getConnection(connectionString, super.getUser(), super.getPassword());
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            //Bei nicht erfolgreichem Verbindungsaufbau Fehler ausgeben und Programm verlasen
            logger.error(e.getMessage());
            logger.error("Could not connect to database. Properties: ");
            logger.error("hostname: " + super.getHost());
            logger.error("database: " + super.getDatabase());
            logger.error("username: " + super.getUser());
            logger.error("passwort: " + super.getPassword());
            System.exit(-1);
        }

    }
}
