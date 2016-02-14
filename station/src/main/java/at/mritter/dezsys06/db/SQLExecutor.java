package at.mritter.dezsys06.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Executes sql statements and begins/ends transactions.
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public class SQLExecutor {

    private Connection connection;
    public static final Logger LOG = LogManager.getLogger(SQLExecutor.class);

    public SQLExecutor(Connection connection) {
        this.connection = connection;
    }

    /**
     * Executes a given sql statement.
     *
     * @param sqlStatement The sql statement
     * @return True if the statement has been executed successfully
     */
    public boolean executeSQL(String sqlStatement) {
        LOG.info("Executing statement: " + sqlStatement);
        boolean success = false;

        try {
            if (!this.connection.isValid(1)) {
                LOG.error("Cannot execute statement: Database connection is closed.");
                return false;
            }
            if (sqlStatement.toUpperCase().startsWith("INSERT") || sqlStatement.toUpperCase().startsWith("UPDATE")) {
                this.connection.prepareStatement(sqlStatement).executeUpdate();
                success = true;
            } else {
                success = this.connection.prepareStatement(sqlStatement).execute();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return success;
    }

    /**
     * Ends the current transaction with either commit or rollback.
     *
     * @param commit True if the current transaction should be committed.
     * @return True if the current transaction has been ended successfully.
     */
    public boolean endTransaction(boolean commit) {
        boolean success = false;
        try {
            if (!this.connection.isValid(1)) {
                LOG.error("Cannot end transaction: Database connection is closed.");
                return false;
            }
            if (commit) {
                LOG.info("Ending transaction with commit");
                this.connection.commit();
            } else {
                LOG.info("Ending transaction with rollback");
                this.connection.rollback();
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

}
