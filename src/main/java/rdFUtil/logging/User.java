package rdFUtil.logging;

public class User extends Login {
    private String name;
    private String surname;
    private String nickname;

    public User(String password, String email, String name, String surname, String nickname){
        super(password,email);
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
    }
}
