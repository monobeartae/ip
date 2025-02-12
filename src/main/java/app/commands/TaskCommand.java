package app.commands;

import app.tasks.Task;

public class TaskCommand extends Command {

    private Task task = null;

    public TaskCommand(CommandType type, Task task) {
        super(type);
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }
}
