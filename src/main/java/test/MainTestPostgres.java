package test;

import utils.DatabaseHelper;

/**
 * @author IvanDan
 */
public class MainTestPostgres {

  public static void main(String[] args) {
    new DatabaseHelper("localhost", 5432, "my_database", "postgres", "postgres");
  }
}
