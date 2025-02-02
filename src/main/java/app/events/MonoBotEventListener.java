package app.events;

import java.util.ArrayList;

import app.tasks.Task;

/**
 * Callback Handler for MonoBot's events
 */
public interface MonoBotEventListener {
    
    public void OnStartBotEvent();
    public void OnStopBotEvent();

    public void OnTaskAddedEvent(Task task, int numTasks);
    public void OnTaskDeletedEvent(Task task, int numTasks);

    public void OnTaskMarkedCompleteEvent(int idx, boolean valid);
    public void OnTaskUnmarkedEvent(int idx, boolean  valid);

    public void OnPrintTasklistEvent(final ArrayList<Task> tasklist);
}
