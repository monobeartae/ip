package app.events;

import app.tasks.Task;
import java.util.ArrayList;

/**
 * Callback Invoker for MonoBot's events
 */
public class MonoBotEventSource {
    protected ArrayList<MonoBotEventListener> listeners = null;

    public MonoBotEventSource() {
        this.listeners = new ArrayList<>();
    }

    public void AddListener(MonoBotEventListener listener) {
        this.listeners.add(listener);
    }
    
    protected void InvokeStartBotEvent() {
        for (MonoBotEventListener listener : listeners) {
            listener.OnStartBotEvent();
        }
    }

    protected void InvokeStopBotEvent() {
        for (MonoBotEventListener listener : listeners) {
            listener.OnStopBotEvent();
        }
    }
    
    protected void InvokeTaskAddedEvent(Task task, int numTasks) {
        for (MonoBotEventListener listener : listeners) {
            listener.OnTaskAddedEvent(task, numTasks);
        }
    }

    protected void InvokeTaskDeletedEvent(Task task, int numTasks) {
        for (MonoBotEventListener listener : listeners) {
            listener.OnTaskDeletedEvent(task, numTasks);
        }
    }

    protected void InvokeTaskMarkedCompleteEvent(int idx, boolean valid) {
        for (MonoBotEventListener listener : listeners) {
            listener.OnTaskMarkedCompleteEvent(idx, valid);
        }
    }
    protected void InvokeTaskUnmarkedEvent(int idx, boolean  valid) {
        for (MonoBotEventListener listener : listeners) {
            listener.OnTaskUnmarkedEvent(idx, valid);
        }
    }

    protected void InvokePrintTasklistEvent(final ArrayList<Task> tasklist) {
        for (MonoBotEventListener listener : listeners) {
            listener.OnPrintTasklistEvent(tasklist);
        }
    }

    protected void InvokeTaskNumberErrorEvent(int num) {
        for (MonoBotEventListener listener : listeners) {
            listener.OnTaskNumberErrorEvent(num);
        }
    }
}
