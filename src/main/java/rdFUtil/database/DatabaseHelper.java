package rdFUtil.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHelper {

    private String host;
    private String user;
    private int port;
    private String database;
    private Connection connection;
    private Statement stmt;

    /**
     * @param h   l'host del server del database
     * @param p   la porta del server del database
     * @param d   il nome del database
     * @param u   il nome utente del database
     * @param pwd la password del database
     */
    public DatabaseHelper(String h, int p, String d, String u, String pwd) {
        this.host = h;
        this.port = p;
        this.user = u;
        this.database = d;

        try (Connection c = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, pwd)) {
            System.out.println("Connesso al database.");
            this.connection = c;
            this.stmt = connection.createStatement();
        } catch (SQLException ex) {
            System.err.println("Connessione fallita.");
            ex.printStackTrace();
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

    public void queryDB(String s) {
        int count = 0;
        try {
          stmt.executeUpdate(s);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
