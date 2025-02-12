package app.monobot;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    private ArrayList<Task> tasks = null;
    private SaveHandler saveHandler = null;

    public MonoBot() {
    }

    /**
     * Starts the bot, initialise variables and load saved tasks
     */
    public void startBot() {
        this.isRunning = true;
        this.saveHandler = new SaveHandler();
        this.tasks = saveHandler.loadTasks();
        this.invokeStartBotEvent();
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Stops the bot, save tasks
     * @throws MonoBotException
     */
    public void stopBot() throws MonoBotException {
        this.saveHandler.saveTasks(this.tasks);
        this.isRunning = false;
        this.invokeStopBotEvent();
    }

    /**
     * Processes command read by inputparser
     * @param cmd Command
     * @throws MonoBotException
     */
    public void processCommand(Command cmd) throws MonoBotException {
        CommandType type = cmd.getType();
        switch (type) {
        case Exit:
            this.stopBot();
            break;
        case AddTask:
            if (!(cmd instanceof TaskCommand)) {
                throw new CommandTypeMismatchException(type, TaskCommand.class, cmd.getClass());
            }
            this.addTask(((TaskCommand) cmd).getTask());
            break;
        case DeleteTask:
            if (!(cmd instanceof TaskIndexCommand)) {
                throw new CommandTypeMismatchException(type, TaskIndexCommand.class, cmd.getClass());
            }
            this.deleteTask(((TaskIndexCommand) cmd).getIndex());
            break;
        case PrintTasklist:
            this.printTaskList();
            break;
        case PrintFindTasklist:
            if (!(cmd instanceof StringCommand)) {
                throw new CommandTypeMismatchException(type, StringCommand.class, cmd.getClass());
            }
            this.printFindTaskList(((StringCommand) cmd).getKeyword());
            break;
        case MarkTask:
            if (!(cmd instanceof TaskIndexCommand)) {
                throw new CommandTypeMismatchException(type, TaskIndexCommand.class, cmd.getClass());
            }
            this.markTaskComplete(((TaskIndexCommand) cmd).getIndex());
            break;
        case UnmarkTask:
            if (!(cmd instanceof TaskIndexCommand)) {
                throw new CommandTypeMismatchException(type, TaskIndexCommand.class, cmd.getClass());
            }
            this.unmarkCompletedTask(((TaskIndexCommand) cmd).getIndex());
            break;
        default:
            break;
        }
    }

    /**
     * Marks a task as complete
     * @param idx Task Number
     * @throws MonoBotException
     */
    private void markTaskComplete(int idx) throws MonoBotException {
        assert(idx >= 0);

        if (idx > this.tasks.size()) {
            throw new InvalidTaskNumberException(idx);
        }
        if (this.tasks.get(idx - 1).getIsCompleted()) {
            this.invokeTaskMarkedCompleteEvent(idx, false);
            return;
        }
        this.tasks.get(idx - 1).markAsComplete();
        this.invokeTaskMarkedCompleteEvent(idx, true);
    }

    /**
     * Unmarks a task from its completion state
     * @param idx Task Number
     * @throws MonoBotException
     */
    private void unmarkCompletedTask(int idx) throws MonoBotException {
        assert(idx >= 0);

        if (idx > this.tasks.size()) {
            throw new InvalidTaskNumberException(idx);
        }
        if (!this.tasks.get(idx - 1).getIsCompleted()) {
            this.invokeTaskUnmarkedEvent(idx, false);
            return;
        }
        this.tasks.get(idx - 1).unmarkCompleted();
        this.invokeTaskUnmarkedEvent(idx, true);
    }

    /**
     * Prints the tasklist
     */
    private void printTaskList() {
        this.invokePrintTasklistEvent(this.tasks);
    }

    /**
     * Prints tasks which contain a match to the specified keyword
     * @param keyword
     */
    private void printFindTaskList(String keyword) {
        ArrayList<Task> tasks = this.tasks.stream().filter(x -> x.isMatchName(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
        this.invokePrintTasklistEvent(tasks);
    }

    /**
     * Adds a task
     * @param task Task to be added
     */
    private void addTask(Task task) {
        this.tasks.add(task);
        this.invokeTaskAddedEvent(task, this.tasks.size());
    }

    /**
     * Deletes a task
     * @param taskNumber Task number to be deleted
     * @throws MonoBotException
     */
    private void deleteTask(int taskNumber) throws MonoBotException {
        assert(taskNumber >= 0);

        if (taskNumber > this.tasks.size()) {
            throw new InvalidTaskNumberException(taskNumber);
        }
        Task t = this.tasks.remove(taskNumber - 1);
        this.invokeTaskDeletedEvent(t, this.tasks.size());
    }
}
