package app.exceptions;

public class InvalidTaskNumberException extends MonoBotException {

    private int taskNumber = 0;

    public InvalidTaskNumberException(int num) {
        this.taskNumber = num;
    }

    @Override
    public String getMessage() {
        return "Task " + taskNumber + " does not exist! Enter 'list' to view list of tasks.";
    }
}
