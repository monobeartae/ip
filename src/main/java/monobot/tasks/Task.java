package monobot.tasks;

/**
 * Represents a Task, base class
 */
public class Task {
    private String taskName = "";
    private boolean isCompleted = false;

    public Task(String name) {
        this.taskName = name;
        this.isCompleted = false;
    }

    /**
     * Marks the task as completed if it is not already
     * @return true if action was previously incomplete, false otherwise
     */
    public boolean MarkAsComplete() {
        if (this.isCompleted)
            return false;
        this.isCompleted = true;
        return true;
    }

    /**
     * Unmarks the task from completion if it is completed
     * @return true if action was previously complete, false otherwise
     */
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

    /**
     * Encodes the task in a format for save data
     * @return Encoded format of task
     */
    public String EncodeTask() {
        return taskName + "|" + isCompleted + "\n";
    }

    /**
     * Decodes an encoded task string and returns the Task object
     * @param line Encoded string representing a task
     * @return Decoded Task object from encoded string
     */
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
