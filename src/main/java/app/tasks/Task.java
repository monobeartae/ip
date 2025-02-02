package app.tasks;

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
     * Marks the task as completed
     */
    public void markAsComplete() {
        this.isCompleted = true;
    }

    /**
     * Unmarks the task from completion
     */
    public void unmarkCompleted() {
        this.isCompleted = false;
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }
   
    /**
     * Checks if the task name contains the keyword given
     * @param keyword keyword
     * @return true if matches, false otherwise
     */
    public boolean isMatchName(String keyword) {
        return this.taskName.contains(keyword);
    }

    @Override
    public String toString() {
        return (this.isCompleted ? "[X] " : "[ ] ") + this.taskName;
    }

    /**
     * Encodes the task in a format for save data
     * @return Encoded format of task
     */
    public String encodeTask() {
        return taskName + "|" + isCompleted + "\n";
    }

    /**
     * Decodes an encoded task string and returns the Task object
     * @param line Encoded string representing a task
     * @return Decoded Task object from encoded string
     */
    public static Task decodeTask(String line) {
        switch (line.charAt(0)) {
        case 'T':
            return Todo.decode(line);
        case 'D':
            return Deadline.decode(line);
        case 'E':
            return Event.decode(line);
        default:
            System.out.println("DecodeTaskFailed: " + line);
            return new Task(line);
        }
    }
}
