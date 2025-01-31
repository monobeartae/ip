package app.monobot;

import app.events.InputParserEventSource;
import app.tasks.Deadline;
import app.tasks.Event;
import app.tasks.Todo;
import app.utility.DateTime;
import java.time.format.DateTimeParseException;

/**
 * Class handling the parsing of user input into actual commands for MonoBot
 */
public class MonoBotInputParser extends InputParserEventSource {

    private MonoBot bot = null;

    public MonoBotInputParser(MonoBot bot) {
        this.bot = bot;
    }

    /**
     * Parses raw user input and processes it
     * @param input User input
     */
    public void ProcessInput(String input) {
        if (input.equals("bye")) {
            this.bot.StopBot();
            return;
        }
        if (input.equals("list")) {
            this.bot.PrintTaskList();
            return;
        }
        if (input.contains("|")) {
            this.InvokeSpecialCharacterErrorEvent("|");
            return;
        }
        String[] split = input.split(" ", 2);
        String cmd = split[0];
        switch (cmd) {
        case "todo":
            this.ProcessTodoInput(split);
            break;
        case "event":
            this.ProcessEventInput(split);
            break;
        case "deadline":
            this.ProcessDeadlineInput(split);
            break;
        case "delete":
            this.ProcessDeleteInput(split);
            break;
        case "mark":
            this.ProcessMarkInput(split);
            break;
        case "unmark":
            this.ProcessUnmarkInput(split);
            break;
        default:
            this.InvokeErrorEvent("Unknown Command! :o");
            break;
        }
    }

    /**
     * Parses input intended for creating a Todo
     * @param split Parsed input
     */
    private void ProcessTodoInput(String[] split) {
        if (split.length < 2) {
            this.InvokeErrorEvent("Todo description is empty! :o");
            return;
        }
        String td_name = split[1];
        this.bot.AddTask(new Todo(td_name));
    }

    /**
     * Parses input intended for creating an Event
     * @param split Parsed input
     */
    private void ProcessEventInput(String[] split) {
        if (split.length < 2) {
            this.InvokeErrorEvent("Event description is empty! :o");
            return;
        }
        if (!split[1].contains(" /from ") || !split[1].contains(" /to ")) {
            this.InvokeErrorEvent("Even if you don't want to go, you have to set the event details! :o");
            return;
        }
        String[] e_split = split[1].split(" /from ");
        String e_name = e_split[0];
        if (e_split.length < 2) {
            this.InvokeErrorEvent("Even if you don't want to go, you have to set the start date! :o");
            return;
        }
        String[] e_split_2 = e_split[1].split(" /to ");
        if (e_split_2.length < 2) {
            this.InvokeErrorEvent("Even if you don't want it to end, you have to set the end date! :o");
            return;
        }
        String from = e_split_2[0];
        String to = e_split_2[1];
        try {
            DateTime start = new DateTime(from);
            DateTime end = new DateTime(to);
            this.bot.AddTask(new Event(e_name, start, end));
        } catch (DateTimeParseException e) {
            this.InvokeErrorEvent(e.getLocalizedMessage());
        }
    }

    /**
     * Parses input intended for creating a Deadline
     * @param split Parsed input
     */
    private void ProcessDeadlineInput(String[] split) {
        if (split.length < 2) {
            this.InvokeErrorEvent("Deadline description is empty! :o");
            return;
        }
        if (!split[1].contains(" /by ")) {
            this.InvokeErrorEvent("Even if you don't want to do it, you have to set the deadline! :o");
            return;
        }
        String[] d_split = split[1].split(" /by ");
        String d_name = d_split[0];
        if (d_split.length < 2) {
            this.InvokeErrorEvent("Even if you don't want to do it, you have to set the deadline! :o");
            return;
        }
        try {
            String deadline = d_split[1];
            this.bot.AddTask(new Deadline(d_name, new DateTime(deadline)));
        } catch (DateTimeParseException e) {
            this.InvokeErrorEvent(e.getLocalizedMessage());
        }
    }

    /**
     * Parses input intended for marking a task complete
     * @param split Parsed input
     */
    private void ProcessMarkInput(String[] split) {
        if (split.length < 2) {
            this.InvokeMissingNumberErrorEvent("mark");
            return;
        }
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            this.InvokeNumberFormatErrorEvent();
            return;
        }
        this.bot.MarkTaskComplete(idx); 
    }
    
    /**
     * Parses input intended for unmarking a task
     * @param split Parsed input
     */
    private void ProcessUnmarkInput(String[] split) {
        if (split.length < 2) {
            this.InvokeMissingNumberErrorEvent("unmark");
            return;
        }
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            this.InvokeNumberFormatErrorEvent();
            return;
        }
        this.bot.UnmarkCompletedTask(idx);
    }

    /**
     * Parses input intended for deleting a task
     * @param split Parsed input
     */
    private void ProcessDeleteInput(String[] split) {
        if (split.length < 2) {
            this.InvokeMissingNumberErrorEvent("delete");
            return;
        }
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            this.InvokeNumberFormatErrorEvent();
            return;
        }
        this.bot.DeleteTask(idx);
    }
}
