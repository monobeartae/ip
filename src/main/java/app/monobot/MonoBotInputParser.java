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
            return this.ProcessTodoInput(split);
        case "event":
            return this.ProcessEventInput(split);
        case "deadline":
            return this.ProcessDeadlineInput(split);
        case "delete":
            return this.ProcessDeleteInput(split);
        case "mark":
            return this.ProcessMarkInput(split);
        case "unmark":
            return this.ProcessUnmarkInput(split);
        case "find":
            return this.ProcessFindInput(split);
        default:
            throw new UnknownCommandException();
        }
        
    }

    private Command ProcessFindInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException("Find search keyword is missing! :o");
        }
        String keyword = split[1];
        // this.bot.PrintFindTaskList(keyword);
        return new StringCommand(CommandType.PrintFindTasklist, keyword);
    }

    private Command ProcessTodoInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException("Todo description is empty! :o");
        }
        String td_name = split[1];
        // this.bot.AddTask(new Todo(td_name));
        return new TaskCommand(CommandType.AddTask,  new Todo(td_name));
    }

    /**
     * Parses input intended for creating an Event
     * @param split Parsed input
     */
    private Command ProcessEventInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException("Event description is empty! :o");
        }
        if (!split[1].contains(" /from ") || !split[1].contains(" /to ")) {
            throw new MonoBotException("Even if you don't want to go, you have to set the event details! :o");
        }
        String[] e_split = split[1].split(" /from ");
        String e_name = e_split[0];
        if (e_split.length < 2) {
            throw new MonoBotException("Even if you don't want to go, you have to set the start date! :o");
        }
        String[] e_split_2 = e_split[1].split(" /to ");
        if (e_split_2.length < 2) {
            throw new MonoBotException("Even if you don't want it to end, you have to set the end date! :o");
        }
        String dt = e_split_2[0];
        try {
            DateTime start = new DateTime(dt);
            dt = e_split_2[1];
            DateTime end = new DateTime(dt);
            return new TaskCommand(CommandType.AddTask, new Event(e_name, start, end));

        } catch (DateTimeParseException e) {
            throw new DateTimeFormatException(dt);
        }
    }

    /**
     * Parses input intended for creating a Deadline
     * @param split Parsed input
     */
    private Command ProcessDeadlineInput(String[] split) throws MonoBotException {
        if (split.length < 2) {
            throw new MonoBotException("Deadline description is empty! :o");
        }
        if (!split[1].contains(" /by ")) {
            throw new MonoBotException("Even if you don't want to do it, you have to set the deadline! :o");
        }
        String[] d_split = split[1].split(" /by ");
        String d_name = d_split[0];
        if (d_split.length < 2) {
            throw new MonoBotException("Even if you don't want to do it, you have to set the deadline! :o");
        }
        String deadline = "";
        try {
            deadline = d_split[1];
            return new TaskCommand(CommandType.AddTask, new Deadline(d_name, new DateTime(deadline)));
        } catch (DateTimeParseException e) {
            throw new DateTimeFormatException(deadline);
        }
    }

    /**
     * Parses input intended for marking a task complete
     * @param split Parsed input
     */
    private Command ProcessMarkInput(String[] split) throws MonoBotException {
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
    private Command ProcessUnmarkInput(String[] split) throws MonoBotException {
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
    private Command ProcessDeleteInput(String[] split) throws MonoBotException {
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
