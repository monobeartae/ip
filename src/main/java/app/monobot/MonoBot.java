package app.monobot;

import app.SaveHandler;
import app.events.MonoBotEventSource;
import app.tasks.Task;
import java.util.ArrayList;

public class MonoBot extends MonoBotEventSource {
    private boolean isRunning = false;

    private ArrayList<Task> tasklist = null;
    private SaveHandler saveHandler = null;
    

    public MonoBot() {
       
    }

    public void StartBot() {
        this.isRunning = true;
        this.saveHandler = new SaveHandler();
        this.tasklist = saveHandler.LoadTasks();
        this.InvokeStartBotEvent();
    }
    
    public boolean IsRunning() { 
        return this.isRunning; 
    }


    public void StopBot() {
        this.saveHandler.SaveTasks(this.tasklist);
        this.isRunning = false;
        this.InvokeStopBotEvent();
    }

    public void MarkTaskComplete(int idx) {
        if (idx > this.tasklist.size()) {
            this.InvokeTaskNumberErrorEvent(idx);
            return;
        }
        boolean valid = this.tasklist.get(idx - 1).MarkAsComplete();
        this.InvokeTaskMarkedCompleteEvent(idx, valid);
    }

    public void UnmarkCompletedTask(int idx) {
        if (idx > this.tasklist.size()) {
            this.InvokeTaskNumberErrorEvent(idx);
            return;
        }
        boolean valid = this.tasklist.get(idx - 1).UnmarkCompleted();
        this.InvokeTaskUnmarkedEvent(idx, valid);
    }

    public void PrintTaskList() {
        this.InvokePrintTasklistEvent(tasklist);
    }
    
    public void AddTask(Task task) {
        this.tasklist.add(task);
        this.InvokeTaskAddedEvent(task, this.tasklist.size());
    }
    
    public void DeleteTask(int task_num) {
        if (task_num > this.tasklist.size()) {
            this.InvokeTaskNumberErrorEvent(task_num);
            return;
        }
        Task t = this.tasklist.remove(task_num - 1);
        this.InvokeTaskDeletedEvent(t, this.tasklist.size());
    }
    
}
