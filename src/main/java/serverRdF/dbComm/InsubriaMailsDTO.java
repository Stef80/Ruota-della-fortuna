package serverRdF.dbComm;

public class InsubriaMailsDTO {
    private String email;
    private String password;
    private UsersDTO user;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }

    public InsubriaMailsDTO(String email, String password, UsersDTO user) {
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public InsubriaMailsDTO(){}
}
