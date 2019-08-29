package serverRdF.emailRdF;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Properties;

public class EmailSender {
  /**
   * Invia delle mail attraverso l'account Insubria del mittente
   *
   * @param from     la mail del mittente
   * @param password la password del mittente
   * @param to       la mail del destinatario
   * @param subject  l'oggetto della mail
   * @param body     il corpo della mail
   * @throws SendFailedException
   * @throws MessagingException In caso non sia stato possibile inviare la mail
   */
  public static void sendUninsubriaEmail(String from, String password, String to, String subject, String body) throws SendFailedException, MessagingException {
    // host per la mail dell'universita'
    String host = "smtp.office365.com";
    // setto le proprieta' smtp
    Properties props = System.getProperties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.starttls.enable", "true");
    // porta per la mail di outlook 587
    props.put("mail.smtp.port", 587);
    // creo la sessione con le proprieta'
    Session session = Session.getInstance(props);
    // creo il messaggio
    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(from));
    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
    msg.setSubject(subject);
    msg.setText(body);
    // invio la mail
    Transport.send(msg, from, password);
//    System.out.println("\nMail was sent successfully.");
  }

  public static void main(String[] argv) {
    try {
      String password = "";
      String username = "";
      String subject = "";
      String to = "";
      String body = "";

      final JTextField uf = new JTextField("name@studenti.uninsubria.it");
      final JPasswordField pf = new JPasswordField();
      final JTextField tf = new JTextField();
      final JTextField sf = new JTextField("email subject");
      final JTextArea bf = new JTextArea(null, "textual content of the email", 10, 20);

      Object[] message = {
              "Username / From:", uf,
              "Password:", pf,
              "To:", tf,
              "Subject:", sf,
              "Body:", bf
      };
      int option = JOptionPane.showOptionDialog(null, message, "Send email",
              JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Send", "Cancel"}, "Send");
      if (option == JOptionPane.YES_OPTION) {
        password = new String(pf.getPassword());
        username = new String(uf.getText());
        to = new String(tf.getText());
        subject = new String(sf.getText());
        body = new String(bf.getText());
        sendUninsubriaEmail(username, password, to, subject, body);
      }
    } catch (MessagingException e) {
      System.err.println("SMTP SEND FAILED:");
      System.err.println(e.getMessage());
    }
  }
}
