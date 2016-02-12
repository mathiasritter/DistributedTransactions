package at.mritter.dezsys06.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * Diese Klasse erweitert den DBConnectionCreator und implementiert die Fabrikmethode zum Erstellen einer Connection
 *
 * @author Ritter Mathias
 */
public class MySQLConnectionCreator extends DBConnectionCreator {

    private static Logger logger = LogManager.getLogger(MySQLConnectionCreator.class.getName());

    /**
     * Im Konstruktor wird der Treiber fuer JDBC geladen
     */
    public MySQLConnectionCreator() {
        try {
            //Laden des MySQL-Treibers
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //Falls nicht gefunden, Fehlermeldung ausgeben und Programm verlassen
            logger.error("MySQL-Treiber fuer JDBC nicht gefunden.");
            logger.error(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * @see DBConnectionCreator#createConnection()
     */
    public Connection createConnection() {

        Connection out = null;

        //Connection-String speziell fuer MySQL
        String connectionString = "jdbc:mysql://" + super.getHost() + "/" + super.getDatabase();
        try {
            //Neue Connection mittels Driver-Manager initialisieren
            out = DriverManager.getConnection(connectionString, super.getUser(), super.getPassword());
        } catch (SQLException e) {
            //Bei nicht erfolgreichem Verbindungsaufbau Fehler ausgeben und Programm verlasen
            logger.error("Verbindung zu DB fehlgeschlagen. Angegebene Daten:");
            logger.error("hostname: " + super.getHost());
            logger.error("database: " + super.getDatabase());
            logger.error("username: " + super.getUser());
            logger.error("passwort: " + super.getPassword());
            System.exit(-1);
        }

        return out;
    }
}
