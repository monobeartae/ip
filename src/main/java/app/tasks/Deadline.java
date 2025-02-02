package app.tasks;

import app.utility.DateTime;

/**
 * Represents a Deadline
 */
public class Deadline extends Task {
    private DateTime deadline = null;

    public Deadline(String name, DateTime deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", this.deadline.formatAsOutputString());
    }

    @Override
    public String encodeTask() {
        return "D|" + this.deadline.formatAsInputString() + "|" + super.encodeTask();
    }

    /**
     * Decodes a string representing an encoded Deadline task object
     * @param line Encoded Deadline task as a string
     * @return Deadline object
     */
    public static Deadline decode(String line) {
        String[] split = line.split("\\|");
        if (split.length != 4) {
            System.out.println("Deadline: Could not decode '" + line + "'. PLease check the format.");
            return null;
        }
        Deadline d = new Deadline(split[2], new DateTime(split[1]));
        if (split[3].equals("true"))
            d.markAsComplete();
        return d;
    }
}
