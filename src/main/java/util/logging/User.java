package util.logging;

import java.io.Serializable;
import java.util.UUID;

/**
 * Una classe che estende {@link Login} per inviare al server i dati necessari alla registrazione. I metodi permettono di accedere e modificare tali dati.
 */
public class User extends Login implements Serializable {
    public static final long serialVersionUID = 1L;
    private String name;
    private String surname;
    private String nickname;
    private String id;

    public User(String password, String email, String name, String surname, String nickname){
        super(password,email);
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getId() {
        return id;
    }
}
