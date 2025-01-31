package monobot.tasks;

import monobot.utility.DateTime;

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
        return "[D]" + super.toString() + String.format(" (by: %s)", this.deadline.AsFormattedOutputString());
    }

    @Override
    public String EncodeTask() {
        return "D|" + this.deadline.AsFormattedInputString() + "|" + super.EncodeTask();
    }

    /**
     * Decodes a string representing an encoded Deadline task object
     * @param line Encoded Deadline task as a string
     * @return Deadline object
     */
    public static Deadline Decode(String line) {
        String[] split = line.split("\\|");
        if (split.length != 4) {
            System.out.println("Deadline: Could not decode '" + line + "'. PLease check the format.");
            return null;
        }
        Deadline d = new Deadline(split[2], new DateTime(split[1]));
        if (split[3].equals("true"))
            d.MarkAsComplete();
        return d;
    }
}
