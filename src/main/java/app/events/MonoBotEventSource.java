package app.events;

import java.util.ArrayList;

import app.tasks.Task;

/**
 * Callback Invoker for MonoBot's events
 */
public class MonoBotEventSource {
    protected ArrayList<MonoBotEventListener> listeners = null;

    public MonoBotEventSource() {
        this.listeners = new ArrayList<>();
    }

    public void attachListener(MonoBotEventListener listener) {
        this.listeners.add(listener);
    }

    protected void invokeStartBotEvent() {
        for (MonoBotEventListener listener : listeners) {
            listener.onStartBotEvent();
        }
    }

    protected void invokeStopBotEvent() {
        for (MonoBotEventListener listener : listeners) {
            listener.onStopBotEvent();
        }
    }

    protected void invokeTaskAddedEvent(Task task, int numTasks) {
        for (MonoBotEventListener listener : listeners) {
            listener.onTaskAddedEvent(task, numTasks);
        }
    }

    protected void invokeTaskDeletedEvent(Task task, int numTasks) {
        for (MonoBotEventListener listener : listeners) {
            listener.onTaskDeletedEvent(task, numTasks);
        }
    }

    protected void invokeTaskMarkedCompleteEvent(int idx, boolean valid) {
        for (MonoBotEventListener listener : listeners) {
            listener.onTaskMarkedCompleteEvent(idx, valid);
        }
    }

    protected void invokeTaskUnmarkedEvent(int idx, boolean valid) {
        for (MonoBotEventListener listener : listeners) {
            listener.onTaskUnmarkedEvent(idx, valid);
        }
    }

    protected void invokePrintTasklistEvent(final ArrayList<Task> tasklist) {
        for (MonoBotEventListener listener : listeners) {
            listener.onPrintTasklistEvent(tasklist);
        }
    }
}
