package dbuilder;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.Notification;

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
                Notification.notify("Successo", "Il database è stato creato con successo.", false);
                dbCreated = true;
            } else {
                Notification.notify("Database già creato", "Il database è già stato creato", true);
            }
        } catch (SQLException ex) {
            Notification.notify("Errore", "Non è stato possibile eseguire l'accesso.", true);
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Notification.notify("Errore", "Non ho trovato il file per la creazione del database", true);
            ex.printStackTrace();
        }
    }
}
