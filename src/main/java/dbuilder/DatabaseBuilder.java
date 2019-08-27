package dbuilder;

import rdFUtil.database.DatabaseHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseBuilder {

    private static DatabaseHelper db;
    private static String url;
    private static BufferedReader br;
    private static StringBuilder sb;
    private static final String PATH = "src/main/java/dbuilder/database_creator.sql";
    private static Scanner scan;
    private static Statement statement;

    public static void main(String[] args) throws SQLException, IOException {
//        try(Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "postgres")){
//            statement=connection.createStatement();
//        }catch (SQLException ex){
//            ex.printStackTrace();
//        }
//        statement.executeQuery();

        File cazzoDiFileCheNonSiLegge = new File(PATH);
        db = new DatabaseHelper("localhost", 5433, "postgres", "postgres", "postgres");
        sb = new StringBuilder("");
        scan=new Scanner(cazzoDiFileCheNonSiLegge);
        while (scan.hasNextLine()) {
            sb.append(scan.nextLine());
            sb.append("\n");
        }
        System.out.println(sb.toString());
        String s=sb.toString();
        db.queryDB(s);
        db.close();
    }
}
