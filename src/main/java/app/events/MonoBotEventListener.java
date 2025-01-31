package app.events;

import app.tasks.Task;
import java.util.ArrayList;

public interface MonoBotEventListener {
    public void OnStartBotEvent();
    public void OnStopBotEvent();

    public void OnTaskAddedEvent(Task task, int numTasks);
    public void OnTaskDeletedEvent(Task task, int numTasks);

    public void OnTaskMarkedCompleteEvent(int idx, boolean valid);
    public void OnTaskUnmarkedEvent(int idx, boolean  valid);

    public void OnPrintTasklistEvent(final ArrayList<Task> tasklist);

    public void OnTaskNumberErrorEvent(int num);
}
