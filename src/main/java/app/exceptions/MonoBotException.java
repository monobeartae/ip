package app.exceptions;

/**
 * Represents Base Exception for all custom exceptions in MonoBot Application
 */
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
