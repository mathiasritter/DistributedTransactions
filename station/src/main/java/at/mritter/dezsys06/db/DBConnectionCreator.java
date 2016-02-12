package at.mritter.dezsys06.db;


import java.sql.Connection;

/**
 * Abstrakte Klasse mit Fabrikmethode zum Erstellen einer neuen Connection zu einer DB.<br>
 * Beinhaelt alle erforderlichen Daten, die zum Verbindungsaufbau erforderlich sind.
 *
 * @author Ritter Mathias
 * @version 1.0
 */
public abstract class DBConnectionCreator {

    private String host;
    private String database;
    private String user;
    private String password;

    /**
     * Default-Werte im parameterlosen Konstruktor setzen
     */
    public DBConnectionCreator() {
        host = "127.0.0.1";
        user = System.getProperty("user.name");
        password = "";
    }

    public abstract Connection createConnection();

    public String getHost() {
        return host;
    }

    /**
     * Setzen des Hostnames.
     * Wird nur gesetzt, wenn dieser nicht null ist.
     *
     * @param host Hostname
     * @return Das aktuelle Objekt (DBConnectionCreator)
     */
    public DBConnectionCreator setHost(String host) {
        if (host != null)
            this.host = host;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    /**
     * Setzen des Datenbank-Namens.
     * Wird nur gesetzt, wenn diese nicht null ist.
     *
     * @param database Datenbank
     * @return Das aktuelle Objekt (DBConnectionCreator)
     */
    public DBConnectionCreator setDatabase(String database) {
        if (database != null)
            this.database = database;
        return this;
    }

    public String getUser() {
        return user;
    }

    /**
     * Setzen des Usernames.
     * Wird nur gesetzt, wenn dieser nicht null ist.
     *
     * @param user Username
     * @return Das aktuelle Objekt (DBConnectionCreator)
     */
    public DBConnectionCreator setUser(String user) {
        if (user != null)
            this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Setzen des Passworts.
     * Wird nur gesetzt, wenn dieses nicht null ist-
     *
     * @param password Passwort
     * @return Das aktuelle Objekt (DBConnectionCreator)
     */
    public DBConnectionCreator setPassword(String password) {
        if (password != null)
            this.password = password;
        return this;
    }




}
