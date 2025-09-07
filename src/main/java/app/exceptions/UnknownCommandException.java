package app.exceptions;

public class UnknownCommandException extends MonoBotException {
    @Override
    public String getMessage() {
        return "Unknown Command! :o";
    }
}
