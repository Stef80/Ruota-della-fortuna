package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

  private String host;
  private String user;
  private int port;
  private String database;

  /**
   *
   * @param h l'host del server del database
   * @param p la porta del server del database
   * @param d il nome del database
   * @param u il nome utente del database
   * @param pwd la password del database
   */
  public DatabaseHelper(String h, int p, String d, String u, String pwd) {
    this.host = h;
    this.port = p;
    this.user = u;
    this.database = d;

    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, pwd)) {
      System.out.println("Connesso al database.");
    } catch (SQLException ex) {
      System.err.println("Connessione fallita.");
      ex.printStackTrace();
    }
  }
}
