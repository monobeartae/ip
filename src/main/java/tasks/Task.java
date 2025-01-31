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

    public String EncodeTask() {
        return taskName + "|" + isCompleted + "\n";
    }

    public static Task DecodeTask(String line) {
        switch (line.charAt(0)) {
        case 'T':
            return Todo.Decode(line);
        case 'D':
            return Deadline.Decode(line);
        case 'E':
            return Event.Decode(line);
        default:
            System.out.println("DecodeTaskFailed: " + line);
            return new Task(line);
        }
    }
}
