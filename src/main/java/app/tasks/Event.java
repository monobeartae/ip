package app.tasks;

import app.utility.DateTime;

/**
 * Represents an Event
 */
public class Event extends Task {

    private DateTime start;
    private DateTime end;
    
    public Event(String name, DateTime start, DateTime end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (from: %s to: %s)", 
                this.start.formatAsOutputString(), this.end.formatAsOutputString());
    }

    @Override
    public String encodeTask() {
        return "E|" + this.start.formatAsInputString() + "|" + this.end.formatAsInputString() + "|" + super.encodeTask();
    }

    /**
     * Decodes a string representing an encoded Event task object
     * @param line Encoded Eodo task as a string
     * @return Event object
     */
    public static Event decode(String line) {
        String[] split = line.split("\\|");
        if (split.length != 5) {
            System.out.println("Event: Could not decode '" + line + "'. PLease check the format.");
            return null;
        }
        Event e = new Event(split[3], new DateTime(split[1]), new DateTime(split[2]));
        if (split[4].equals("true"))
            e.markAsComplete();
        return e;
    }
}
