package app.monobot;

import java.util.ArrayList;
import java.util.function.Consumer;

import app.events.MonoBotEventListener;
import app.tasks.Task;

/**
 * Handles GUI Printing for MonoBot
 */
public class MonoBotGuiHandler implements MonoBotEventListener {
    private Consumer<String> sendBotMsgFunction = null;

    private final String[] MESSAGE_FORMAT_WELCOME = new String[] {
            "Hi There! I'm Mono. What can I do for you today?",
            "--COMMANDS--",
            "list - view tasklist",
            "todo <task_name> - add a todo task",
            "deadline <task_name> /by <deadline: d/M/yyyy HHmm> - add a deadline task",
            "event <task_name> /from <start: d/M/yyyy HHmm> /to <end: d/M/yyyy HHmm> - add an event task",
            "mark <task_number> - mark a task complete",
            "unmark <task_number> - unmark a completed task",
            "delete <task_number> - delete a task",
            "find <keyword> - search for tasks",
            "help - print command list"};
    private final String MESSAGE_FORMAT_GOODBYE = "Goodbye :( See you again soon!";
    private final String MESSAGE_FORMAT_EMPTY_TASKLIST = "You have no tasks!!";
    private final String MESSAGE_FORMAT_ERROR = "\\(T o T)'/ %s";
    private final String MESSAGE_FORMAT_TASK_ACTION =
            "Got it! I've %s this task for you:\n-> %s\nNow you have %d task(s) in your list :D";
    private final String MESSAGE_FORMAT_MARK_SUCCESS = "Task %d has been marked complete!";
    private final String MESSAGE_FORMAT_MARK_FAILURE = "Task %d is already completed!";
    private final String MESSAGE_FORMAT_UNMARK_SUCCESS = "Task %d has been unmarked!";
    private final String MESSAGE_FORMAT_UNMARK_FAILURE = "Task %d has not yet been completed!";

    public MonoBotGuiHandler(MonoBot bot, Consumer<String> sendBotMsgFunction) {
        bot.attachListener(this);
        this.sendBotMsgFunction = sendBotMsgFunction;
    }

    @Override
    public void onStartBotEvent() {
        this.sendMessage(this.MESSAGE_FORMAT_WELCOME);
    }

    @Override
    public void onStopBotEvent() {
        this.sendMessage(this.MESSAGE_FORMAT_GOODBYE);
    }

    @Override
    public void onPrintCommandsEvent() {
        this.sendMessage(this.MESSAGE_FORMAT_WELCOME);
    }

    @Override
    public void onTaskAddedEvent(Task task, int numTasks) {
        String msg = String.format(this.MESSAGE_FORMAT_TASK_ACTION,
                "added", task.toString(), numTasks);
        this.sendMessage(msg);
    }

    @Override
    public void onTaskDeletedEvent(Task task, int numTasks) {
        String msg = String.format(this.MESSAGE_FORMAT_TASK_ACTION,
                "removed", task.toString(), numTasks);
        this.sendMessage(msg);
    }

    @Override
    public void onTaskMarkedCompleteEvent(int idx, boolean valid) {
        this.sendMessage(String.format(valid ? this.MESSAGE_FORMAT_MARK_SUCCESS
                : this.MESSAGE_FORMAT_MARK_FAILURE, idx));
    }

    @Override
    public void onTaskUnmarkedEvent(int idx, boolean valid) {
        this.sendMessage(String.format(valid ? this.MESSAGE_FORMAT_UNMARK_SUCCESS
                : this.MESSAGE_FORMAT_UNMARK_FAILURE, idx));
    }

    @Override
    public void onPrintTasklistEvent(final ArrayList<Task> tasklist) {
        if (tasklist.isEmpty()) {
            this.sendMessage(this.MESSAGE_FORMAT_EMPTY_TASKLIST);
            return;
        }
        String s = "";
        for (int i = 0; i < tasklist.size(); i++) {
            s += String.format("%d. %s\n", i + 1, tasklist.get(i).toString());
        }
        this.sendMessage(s);
    }

    /**
     * Sends an error message in the specified format
     * @param context Error message to print
     */
    public void sendErrorMessage(String context) {
        this.sendBotMsgFunction.accept(String.format(this.MESSAGE_FORMAT_ERROR, context));
    }


    /**
     * Sends a message to the chat window as the bot
     * @param msg
     */
    private void sendMessage(String msg) {
        this.sendBotMsgFunction.accept(msg);
    }

    /**
     * Sends lines of messages to the chat window as the bot
     * @param msg
     */
    private void sendMessage(String... msgs) {
        String msg = "";
        for (String s : msgs) {
            msg += s + "\n";
        }
        this.sendMessage(msg);
    }
}
