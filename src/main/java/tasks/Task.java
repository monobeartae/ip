package tasks;

public class Task {
    private String taskName;
    private boolean isCompleted;

    public Task(String name) {
        this.taskName = name;
        this.isCompleted = false;
    }
    public boolean MarkAsComplete() {
        if (this.isCompleted)
            return false;
        this.isCompleted = true;
        return true;
    }
    public boolean UnmarkCompleted() {
        if (!this.isCompleted)
            return false;
        this.isCompleted = false;
        return true;
    }
    @Override
    public String toString() {
        return (this.isCompleted ? "[X] " : "[ ] ") + this.taskName;
    }
}
