package app.monobot;

import java.time.format.DateTimeParseException;

import app.commands.Command;
import app.commands.CommandType;
import app.commands.StringCommand;
import app.commands.TaskCommand;
import app.commands.TaskIndexCommand;
import app.exceptions.DateTimeFormatException;
import app.exceptions.MissingTaskNumberException;
import app.exceptions.MonoBotException;
import app.exceptions.SpecialCharacterException;
import app.exceptions.UnknownCommandException;
import app.tasks.Deadline;
import app.tasks.Event;
import app.tasks.Todo;
import app.utility.DateTime;

/**
 * Class handling the parsing of user input into actual commands for MonoBot
 */
public class MonoBotInputParser {

    private final String MSG_FORMAT_MISSING_DETAILS = "%s is empty/missing! :o";
    private final String MSG_FORMAT_MISSING_DATETIME = "Even if you don't want %s, you still have to set %s! :o";

    public MonoBotInputParser() {
    }

    /**
     * Parses raw user input and processes it
     * @param input User input
     */
    public Command processInput(String input) throws MonoBotException {
        if (input.equals("bye")) {
            return new Command(CommandType.Exit);
        }
        if (input.equals("list")) {
            return new Command(CommandType.PrintTasklist);
        }
        if (input.equals("help")) {
            return new Command(CommandType.Help);
        }
        if (input.contains("|")) {
            throw new SpecialCharacterException("|");
        }
        String[] split = input.split(" ", 2);
        String cmd = split[0];
        switch (cmd) {
        case "todo":
            return this.processTodoInput(split);
        case "event":
            return this.processEventInput(split);
        case "deadline":
            return this.processDeadlineInput(split);
        case "delete":
            return this.processDeleteInput(split);
        case "mark":
            return this.processMarkInput(split);
        case "unmark":
            return this.processUnmarkInput(split);
        case "find":
            return this.processFindInput(split);
        default:
            throw new UnknownCommandException();
        }
    }

    private Command processFindInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DETAILS, "Find search keyword"));
        }
        String keyword = split[1];
        return new StringCommand(CommandType.PrintFindTasklist, keyword);
    }

    private Command processTodoInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DETAILS, "Todo description"));
        }
        String tdName = split[1];
        return new TaskCommand(CommandType.AddTask, new Todo(tdName));
    }

    /**
     * Parses input intended for creating an Event
     * @param split Parsed input
     */
    private Command processEventInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DETAILS, "Event description"));
        }
        if (!split[1].contains(" /from ") || !split[1].contains(" /to ")) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DATETIME, "to go", "the event details"));
        }
        String[] eSplit = split[1].split(" /from ");
        String eName = eSplit[0];
        if (eSplit.length < 2) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DATETIME, "to go", "the start date"));
        }
        String[] eSplit2 = eSplit[1].split(" /to ");
        if (eSplit2.length < 2) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DATETIME, "it to end", "the end date"));
        }
        DateTime start = parseToDateTime(eSplit2[0]);
        DateTime end = parseToDateTime(eSplit2[1]);
        return new TaskCommand(CommandType.AddTask, new Event(eName, start, end));
    }

    /**
     * Parses input intended for creating a Deadline
     * @param split Parsed input
     */
    private Command processDeadlineInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DETAILS, "Deadline description"));
        }
        if (!split[1].contains(" /by ")) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DATETIME, "to do it", "the deadline"));
        }
        String[] dSplit = split[1].split(" /by ");
        String dName = dSplit[0];
        if (dSplit.length < 2) {
            throw new MonoBotException(String.format(this.MSG_FORMAT_MISSING_DATETIME, "to do it", "the deadline"));
        }
        DateTime deadline = parseToDateTime(dSplit[1]);
        return new TaskCommand(CommandType.AddTask, new Deadline(dName, deadline));
    }

    /**
     * Parses input intended for marking a task complete
     * @param split Parsed input
     */
    private Command processMarkInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MissingTaskNumberException("mark");
        }
        int idx = parseToInteger(split[1]);
        return new TaskIndexCommand(CommandType.MarkTask, idx);
    }

    /**
     * Parses input intended for unmarking a task
     * @param split Parsed input
     */
    private Command processUnmarkInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MissingTaskNumberException("unmark");
        }
        int idx = parseToInteger(split[1]);
        return new TaskIndexCommand(CommandType.UnmarkTask, idx);

    }

    /**
     * Parses input intended for deleting a task
     * @param split Parsed input
     */
    private Command processDeleteInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MissingTaskNumberException("delete");
        }
        int idx = parseToInteger(split[1]);
        return new TaskIndexCommand(CommandType.DeleteTask, idx);
    }

    /**
     * Parses a string into integer
     * @param str string to be parsed
     */
    private int parseToInteger(String str) throws app.exceptions.NumberFormatException {
        try {
            int idx = Integer.parseInt(str);
            return idx;
        } catch (NumberFormatException e) {
            throw new app.exceptions.NumberFormatException();
        }
    }

    /**
     * Parses a string into datetime
     * @param input string to be parsed
     */
    private DateTime parseToDateTime(String input) throws DateTimeFormatException {
        try {
            DateTime dt = new DateTime(input);
            return dt;
        } catch (DateTimeParseException e) {
            throw new DateTimeFormatException(input);
        }
    }
}
