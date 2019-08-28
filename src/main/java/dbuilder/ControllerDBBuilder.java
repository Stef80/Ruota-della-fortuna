package dbuilder;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rdFUtil.Notification;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ControllerDBBuilder {

    public TextField textFieldHost;
    public TextField textFieldPort;
    public TextField textFieldUser;
    public PasswordField textFieldPassword;
    public Button buttonCreateTable;

    private StringBuilder sb;
    private final String PATH = "src/main/java/dbuilder/database_creator.sql";
    private Scanner scan;
    private Statement statement;
    private String host;
    private String port;
    private String user;
    private boolean dbCreated = false;

    public void buttonPressed() {
        host = textFieldHost.getText();
        port = textFieldPort.getText();
        user = textFieldUser.getText();
        createTable(host, port, user, textFieldPassword.getText());
    }

    private void createTable(String host, String port, String user, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/postgres", user, password)) {
            if (!dbCreated) {
                statement = connection.createStatement();
                File sqlCreator = new File(PATH);
                sb = new StringBuilder("");
                scan = new Scanner(sqlCreator);
                while (scan.hasNextLine()) {
                    sb.append(scan.nextLine());
                }
                String s = sb.toString();
                System.out.println(s);
                statement.executeUpdate(s);
                Notification.notification("Successo", "Il databse è stato creato con successo.", 5, false);
                dbCreated = true;
            } else {
                Notification.notification("Database già creato", "Il database è già stato creato", 5, true);
            }
        } catch (SQLException ex) {
            Notification.notification("Errore", "Non è stato possibile eseguire l'accesso.", 5, true);
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Notification.notification("Errore", "Non ho trovato il file per la creazione del database", 5, true);
            ex.printStackTrace();
        }
    }
}
