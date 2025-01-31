package app.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Date and Time
 */
public class DateTime {
    private LocalDateTime datetime = null;

    private static DateTimeFormatter inputFormatter = null;
    private static DateTimeFormatter outputFormatter = null;

    private static final String INPUT_FORMAT = "d/M/yyyy HHmm";
    private static final String OUTPUT_FORMAT = "d MMM yyyy h.mm a";

    public DateTime(String inputText) throws DateTimeParseException {
        this.datetime = LocalDateTime.parse(inputText, DateTime.GetInputFormatter());
    }

    /**
     * Formats the date and time according to OUTPUT_FORMAT
     * @return Formatted DateTime String
     */
    public String AsFormattedOutputString() {
        return this.datetime.format(DateTime.GetOutputFormatter());
    }

    /**
     * Formats the date and time according to INPUT_FORMAT
     * @return Formatted DateTime String
     */
    public String AsFormattedInputString() {
        return this.datetime.format(DateTime.GetInputFormatter());
    }

    private static DateTimeFormatter GetOutputFormatter() {
        if (outputFormatter == null) {
            outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_FORMAT);
        }
        return outputFormatter;
    }

    private static DateTimeFormatter GetInputFormatter() {
        if (inputFormatter == null) {
            inputFormatter = DateTimeFormatter.ofPattern(INPUT_FORMAT);
        }
        return inputFormatter;
    }
}
