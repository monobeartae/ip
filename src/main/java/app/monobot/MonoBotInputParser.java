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

    public MonoBotInputParser() {
    }

    /**
     * Parses raw user input and processes it
     * @param input User input
     */
    public Command processInput(String input) throws MonoBotException {
        if (input.equals("bye")) {
            // this.bot.StopBot();
            return new Command(CommandType.Exit);
        }
        if (input.equals("list")) {
            // this.bot.PrintTaskList();
            return new Command(CommandType.PrintTasklist);
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
            throw new MonoBotException("Find search keyword is missing! :o");
        }
        String keyword = split[1];
        // this.bot.PrintFindTaskList(keyword);
        return new StringCommand(CommandType.PrintFindTasklist, keyword);
    }

    private Command processTodoInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException("Todo description is empty! :o");
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
            throw new MonoBotException("Event description is empty! :o");
        }
        if (!split[1].contains(" /from ") || !split[1].contains(" /to ")) {
            throw new MonoBotException("Even if you don't want to go, you have to set the event details! :o");
        }
        String[] eSplit = split[1].split(" /from ");
        String eName = eSplit[0];
        if (eSplit.length < 2) {
            throw new MonoBotException("Even if you don't want to go, you have to set the start date! :o");
        }
        String[] eSplit2 = eSplit[1].split(" /to ");
        if (eSplit2.length < 2) {
            throw new MonoBotException("Even if you don't want it to end, you have to set the end date! :o");
        }
        String dt = eSplit2[0];
        try {
            DateTime start = new DateTime(dt);
            dt = eSplit2[1];
            DateTime end = new DateTime(dt);
            return new TaskCommand(CommandType.AddTask, new Event(eName, start, end));

        } catch (DateTimeParseException e) {
            throw new DateTimeFormatException(dt);
        }
    }

    /**
     * Parses input intended for creating a Deadline
     * @param split Parsed input
     */
    private Command processDeadlineInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException("Deadline description is empty! :o");
        }
        if (!split[1].contains(" /by ")) {
            throw new MonoBotException("Even if you don't want to do it, you have to set the deadline! :o");
        }
        String[] dSplit = split[1].split(" /by ");
        String dName = dSplit[0];
        if (dSplit.length < 2) {
            throw new MonoBotException("Even if you don't want to do it, you have to set the deadline! :o");
        }
        String deadline = "";
        try {
            deadline = dSplit[1];
            return new TaskCommand(CommandType.AddTask, new Deadline(dName, new DateTime(deadline)));
        } catch (DateTimeParseException e) {
            throw new DateTimeFormatException(deadline);
        }
    }

    /**
     * Parses input intended for marking a task complete
     * @param split Parsed input
     */
    private Command processMarkInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MissingTaskNumberException("mark");
        }
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            throw new app.exceptions.NumberFormatException();
        }
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
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            throw new app.exceptions.NumberFormatException();
        }
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
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            throw new app.exceptions.NumberFormatException();
        }
        return new TaskIndexCommand(CommandType.DeleteTask, idx);
    }
}
