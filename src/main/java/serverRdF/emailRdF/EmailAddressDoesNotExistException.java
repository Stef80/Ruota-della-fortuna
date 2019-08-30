package serverRdF.emailRdF;

public class EmailAddressDoesNotExistException extends RuntimeException {
    public EmailAddressDoesNotExistException(){
        super("Non e' stato possibile inviare la mail");
    }
}
