package app.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Date and Time
 */
public class DateTime {
    public static final String INPUT_FORMAT = "d/M/yyyy HHmm";
    private static final String OUTPUT_FORMAT = "d MMM yyyy h.mm a";

    private static DateTimeFormatter inputFormatter = null;
    private static DateTimeFormatter outputFormatter = null;


    private LocalDateTime datetime = null;

    public DateTime(String inputText) throws DateTimeParseException {
        this.datetime = LocalDateTime.parse(inputText, DateTime.getInputFormatter());
    }

    /**
     * Formats the date and time according to OUTPUT_FORMAT
     * @return Formatted DateTime String
     */
    public String formatAsOutputString() {
        return this.datetime.format(DateTime.getOutputFormatter());
    }

    /**
     * Formats the date and time according to INPUT_FORMAT
     * @return Formatted DateTime String
     */
    public String formatAsInputString() {
        return this.datetime.format(DateTime.getInputFormatter());
    }

    /**
     * Checks if given datetime is before stored datetime
     */
    public boolean isDateTimeBefore(DateTime dt) {
        return this.datetime.isBefore(dt.datetime);
    }

    private static DateTimeFormatter getOutputFormatter() {
        if (outputFormatter == null) {
            outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_FORMAT);
        }
        return outputFormatter;
    }

    private static DateTimeFormatter getInputFormatter() {
        if (inputFormatter == null) {
            inputFormatter = DateTimeFormatter.ofPattern(INPUT_FORMAT);
        }
        return inputFormatter;
    }
}
