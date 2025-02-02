package app.monobot;

import java.util.ArrayList;

import app.SaveHandler;
import app.commands.Command;
import app.commands.CommandType;
import app.commands.StringCommand;
import app.commands.TaskCommand;
import app.commands.TaskIndexCommand;
import app.events.MonoBotEventSource;
import app.exceptions.CommandTypeMismatchException;
import app.exceptions.InvalidTaskNumberException;
import app.exceptions.MonoBotException;
import app.tasks.Task;

/**
 * Class handling MonoBot's Task Management Logic
 */
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


    public void StopBot() throws MonoBotException {
        this.saveHandler.SaveTasks(this.tasklist);
        this.isRunning = false;
        this.InvokeStopBotEvent();
    }

    public void processCommand(Command cmd) throws MonoBotException {
        CommandType type = cmd.getType();
        switch (type) {
        case Exit:
            this.StopBot();
            break;
        case AddTask:
            if (!(cmd instanceof TaskCommand)) {
                throw new CommandTypeMismatchException(type, TaskCommand.class, cmd.getClass());
            }
            this.AddTask(((TaskCommand)cmd).getTask());
            break;
        case DeleteTask:
            if (!(cmd instanceof TaskIndexCommand)) {
                throw new CommandTypeMismatchException(type, TaskIndexCommand.class, cmd.getClass());
            }
            this.DeleteTask(((TaskIndexCommand)cmd).getIndex());
            break;
        case PrintTasklist:
            this.PrintTaskList();
            break;
        case PrintFindTasklist:
            if (!(cmd instanceof StringCommand)) {
                throw new CommandTypeMismatchException(type, StringCommand.class, cmd.getClass());
            }
            this.PrintFindTaskList(((StringCommand)cmd).getKeyword());
            break;
        case MarkTask:
            if (!(cmd instanceof TaskIndexCommand)) {
                throw new CommandTypeMismatchException(type, TaskIndexCommand.class, cmd.getClass());
            }
            this.MarkTaskComplete(((TaskIndexCommand)cmd).getIndex());
            break;
        case UnmarkTask:
            if (!(cmd instanceof TaskIndexCommand)) {
                throw new CommandTypeMismatchException(type, TaskIndexCommand.class, cmd.getClass());
            }
            this.UnmarkCompletedTask(((TaskIndexCommand)cmd).getIndex());
            break;
        default:
            break;
        }
    }

    private void MarkTaskComplete(int idx) throws MonoBotException {
        if (idx > this.tasklist.size()) {
            throw new InvalidTaskNumberException(idx);
        }
        boolean valid = this.tasklist.get(idx - 1).MarkAsComplete();
        this.InvokeTaskMarkedCompleteEvent(idx, valid);
    }

    private void UnmarkCompletedTask(int idx) throws MonoBotException {
        if (idx > this.tasklist.size()) {
            throw new InvalidTaskNumberException(idx);
        }
        boolean valid = this.tasklist.get(idx - 1).UnmarkCompleted();
        this.InvokeTaskUnmarkedEvent(idx, valid);
    }

    private void PrintTaskList() {
        this.InvokePrintTasklistEvent(this.tasklist);
    }

    private void PrintFindTaskList(String keyword) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : this.tasklist) {
            if (task.MatchName(keyword)) {
                tasks.add(task);
            }
        }
        this.InvokePrintTasklistEvent(tasks);
    }
    
    private void AddTask(Task task) {
        this.tasklist.add(task);
        this.InvokeTaskAddedEvent(task, this.tasklist.size());
    }
    
    private void DeleteTask(int taskNumber) throws MonoBotException {
        if (taskNumber > this.tasklist.size()) {
            throw new InvalidTaskNumberException(taskNumber);
        }
        Task t = this.tasklist.remove(taskNumber - 1);
        this.InvokeTaskDeletedEvent(t, this.tasklist.size());
    }
}
