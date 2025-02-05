package app.monobot;

import java.util.ArrayList;
import java.util.function.Consumer;

import app.events.MonoBotEventListener;
import app.tasks.Task;

public class MonoBotGUIHandler implements MonoBotEventListener {
    private Consumer<String> sendBotMsgFunction = null;

    public MonoBotGUIHandler(MonoBot bot, Consumer<String> sendBotMsgFunction) {
        bot.attachListener(this);
        this.sendBotMsgFunction = sendBotMsgFunction;
    }

    @Override
    public void onStartBotEvent() {
        String[] welcomeMessages = {
            "Hi There! I'm Mono. What can I do for you today?",
            "",
            "list - view tasklist",
            "todo <task_name> - add a todo task",
            "deadline <task_name> /by <deadline: d/M/yyyy HHmm> - add a deadline task",
            "event <task_name> /from <start: d/M/yyyy HHmm> /to <end: d/M/yyyy HHmm> - add an event task",
            "mark <task_number> - mark a task complete",
            "unmark <task_number> - unmark a completed task",
            "delete <task_number> - delete a task",
            "find <keyword> - search for tasks"};

        this.sendMessage(welcomeMessages);
    }

    @Override
    public void onStopBotEvent() {
        this.sendMessage("Goodbye :( See you again soon!");
    }

    @Override
    public void onTaskAddedEvent(Task task, int numTasks) {
        String msg = String.format("%s\n%s\n%s",
            "Got it! I've added this task for you:",
            "-> " + task.toString(),
            "Now you have " + numTasks + " task(s) in your list :D");
        this.sendMessage(msg);
    }

    @Override
    public void onTaskDeletedEvent(Task task, int numTasks) {
        String msg = String.format("%s\n%s\n%s",
            "Got it! I've removed this task for you:",
            "-> " + task.toString(),
            "Now you have " + (numTasks) + " task(s) in your list :D");
        this.sendMessage(msg);
    }

    @Override
    public void onTaskMarkedCompleteEvent(int idx, boolean valid) {
        this.sendMessage("Task " + idx + (valid ? " has been marked complete!" : " is already completed!"));
        
    }

    @Override
    public void onTaskUnmarkedEvent(int idx, boolean valid) {
        this.sendMessage("Task " + idx + (valid ? " has been unmarked!" : " has not been completed!"));
    }

    @Override
    public void onPrintTasklistEvent(final ArrayList<Task> tasklist) {
        if (tasklist.isEmpty()) {
            this.sendMessage("You have no tasks!");
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
        this.sendBotMsgFunction.accept("\\(T o T)'/ " + context);
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
