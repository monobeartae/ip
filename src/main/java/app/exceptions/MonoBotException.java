package app.exceptions;

public class MonoBotException extends Exception {
    private String message = "";

    protected MonoBotException() {
        
    }

    public MonoBotException(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
