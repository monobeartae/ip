package app.exceptions;

public class NumberFormatException extends MonoBotException {

    @Override
    public String getMessage() {
        return "Do you perhaps not know what a number is? :o";
    }
}
