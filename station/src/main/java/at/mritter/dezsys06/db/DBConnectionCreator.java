package at.mritter.dezsys06.db;


import java.sql.Connection;

/**
 * Class to get a new database connection. Parameters need to be set before getting the connection
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public abstract class DBConnectionCreator {

    private String host;
    private String database;
    private String user;
    private String password;

    /**
     * Sets default values: host = localhost, user = system username, password = none.
     */
    public DBConnectionCreator() {
        host = "127.0.0.1";
        user = System.getProperty("user.name");
        password = "";
    }

    /**
     * Get the database connection. Connection will be created if it has not been established yet.
     */
    public abstract Connection getConnection();

    public String getHost() {
        return host;
    }


    public DBConnectionCreator setHost(String host) {
        if (host != null)
            this.host = host;
        return this;
    }

    public String getDatabase() {
        return database;
    }


    public DBConnectionCreator setDatabase(String database) {
        if (database != null)
            this.database = database;
        return this;
    }

    public String getUser() {
        return user;
    }


    public DBConnectionCreator setUser(String user) {
        if (user != null)
            this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DBConnectionCreator setPassword(String password) {
        if (password != null)
            this.password = password;
        return this;
    }




}
