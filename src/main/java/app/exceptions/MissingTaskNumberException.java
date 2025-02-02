package app.exceptions;

public class MissingTaskNumberException extends MonoBotException {
    private String action = "";

    public MissingTaskNumberException(String action) {
        this.action = action;
    }

    @Override
    public String getMessage() {
        return "Which task did you want to " + this.action + "? I didn't quite catch that! :o";
    }
}
