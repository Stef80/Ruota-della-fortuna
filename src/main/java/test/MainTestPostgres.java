package test;

import java.sql.SQLException;
import utils.DatabaseHelper;

/**
 * @author IvanDan
 */
public class MainTestPostgres {

  public static void main(String[] args) throws SQLException {
    DatabaseHelper dbh = new DatabaseHelper("localhost", 5432, "postgres", "postgres", "");
    dbh.queryDB("create table prova ("
            + "codice varchar(5) primary key,"
            + "nome varchar(50) not null,"
            + "età decimal(3) not null);");
    dbh.close();
  }
}
