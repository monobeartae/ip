package tasks;

public class Task {
    protected String taskName;

    public Task(String name) {
        this.taskName = name;
    }
    @Override
    public String toString() {
        return taskName;
    }
}
