package app.exceptions;

import app.utility.DateTime;

public class DateTimeFormatException extends MonoBotException {

    private String givenInput = "";
    public DateTimeFormatException(String input) {
        this.givenInput = input;
    }

    @Override
    public String getMessage() {
        return String.format("nani is '%s'? Please give the date and time in the format '%s'! i.e. 3/1/2025 1800",
                this.givenInput, DateTime.INPUT_FORMAT);
    }

}
