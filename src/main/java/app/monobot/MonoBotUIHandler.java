package app.monobot;

import app.events.InputParserEventListener;
import app.events.MonoBotEventListener;
import app.tasks.Task;
import java.util.ArrayList;

public class MonoBotUIHandler implements MonoBotEventListener, InputParserEventListener {

    private final String INDENT = "   ";
    private final String SEPARATOR = "________________\n";

    public MonoBotUIHandler(MonoBot bot, MonoBotInputParser inputParser) {
        bot.AddListener(this);
        inputParser.AddListener(this);
    }

    @Override
    public void OnStartBotEvent() {
        String[] welcomeMessages = new String[] {
            "Hi There! I'm Mono. What can I do for you today?",
            "",
            "list - view tasklist",
            "todo <task_name> - add a todo task",
            "deadline <task_name> /by <deadline: d/M/yyyy HHmm> - add a deadline task",
            "event <task_name> /from <start: d/M/yyyy HHmm> /to <end: d/M/yyyy HHmm> - add an event task",
            "mark <task_number> - mark a task complete",
            "unmark <task_number> - unmark a completed task",
            "delete <task_number> - delete a task",
            "find <keyword> - search for tasks"

        };
        printMessage(welcomeMessages);
    }

    @Override
    public void OnStopBotEvent() {
        printMessage("Goodbye :( See you again soon!");
    }

    @Override
    public void OnTaskAddedEvent(Task task, int numTasks) {
        String[] msg = new String[] {
            "Got it! I've added this task for you:",
            "-> " + task.toString(),
            "Now you have " + numTasks + " task(s) in your list :D"
        };
        this.printMessage(msg);
    }

    @Override
    public void OnTaskDeletedEvent(Task task, int numTasks) {
        String[] msg = new String[] {
            "Got it! I've removed this task for you:",
            "-> " + task.toString(),
            "Now you have " + (numTasks) + " task(s) in your list :D"
        };
        this.printMessage(msg);
    }

    @Override
    public void OnTaskMarkedCompleteEvent(int idx, boolean valid) {
        this.printErrorMessage("Task " + idx + (valid ? " has been marked complete!" : " is already completed!"));
        
    }

    @Override
    public void OnTaskUnmarkedEvent(int idx, boolean valid) {
        this.printErrorMessage("Task " + idx + (valid ? " has been unmarked!" : " has not been completed!"));
    }

    @Override
    public void OnPrintTasklistEvent(final ArrayList<Task> tasklist) {
        if (tasklist.isEmpty()) {
            this.printMessage("You have no tasks!");
            return;
        }
        String[] list = new String[tasklist.size()];
        for (int i = 0; i < tasklist.size(); i++)
            list[i] = String.format("%d. %s", i + 1, tasklist.get(i).toString());
        this.printMessage(list);
    }

    @Override
    public void OnTaskNumberErrorEvent(int num) {
        this.printErrorMessage("Task " + num + " does not exist! Enter 'list' to view list of tasks.");
    }

    @Override
    public void OnSpecialCharacterErrorEvent(String character) {
        this.printErrorMessage("Please don't use'" + character + "'! It's my special character, MINE!");
    }

    @Override
    public void OnNumberFormatErrorEvent() {
        this.printErrorMessage("Do you perhaps not know what a number is? :o");
    }

    @Override
    public void OnMissingNumberErrorEvent(String action) {
        this.printErrorMessage("Which task did you want to " + action + "? I didn't quite catch that! :o");
    }

    @Override
    public void OnErrorEvent(String errorMsg) {
        this.printErrorMessage(errorMsg);
    }


    private void printMessage(String msg) {
        System.out.println(this.INDENT + this.SEPARATOR);
        System.out.println(this.INDENT + msg);
        System.out.println(this.INDENT + this.SEPARATOR);
    }

    private void printErrorMessage(String context) {
        this.printMessage("\\(T o T)'/ " + context);
    }

    private void printMessage(String[] msg) {
        System.out.println(this.INDENT + this.SEPARATOR);
        for (String s : msg)
            System.out.println(this.INDENT + s);
        System.out.println(this.INDENT + this.SEPARATOR);
    }

    private void printMessage(ArrayList<String> msg) {
        System.out.println(this.INDENT + this.SEPARATOR);
        for (String s : msg)
            System.out.println(this.INDENT + s);
        System.out.println(this.INDENT + this.SEPARATOR);
    }
}
