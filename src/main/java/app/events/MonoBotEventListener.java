package app.events;

import java.util.ArrayList;

import app.tasks.Task;

/**
 * Callback Handler for MonoBot's events
 */
public interface MonoBotEventListener {

    public void onStartBotEvent();
    public void onStopBotEvent();

    public void onTaskAddedEvent(Task task, int numTasks);
    public void onTaskDeletedEvent(Task task, int numTasks);

    public void onTaskMarkedCompleteEvent(int idx, boolean valid);
    public void onTaskUnmarkedEvent(int idx, boolean valid);

    public void onPrintTasklistEvent(final ArrayList<Task> tasklist);
    public void onPrintCommandsEvent();
}
