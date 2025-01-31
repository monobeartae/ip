package tasks;

import utility.DateTime;

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
                this.start.AsFormattedOutputString(), this.end.AsFormattedOutputString());
    }

    @Override
    public String EncodeTask() {
        return "E|" + this.start.AsFormattedInputString() + "|" + this.end.AsFormattedInputString() + "|" + super.EncodeTask();
    }

    public static Event Decode(String line) {
        String[] split = line.split("\\|");
        if (split.length != 5) {
            System.out.println("Event: Could not decode '" + line + "'. PLease check the format.");
            return null;
        }
        Event e = new Event(split[3], new DateTime(split[1]), new DateTime(split[2]));
        if (split[4].equals("true"))
            e.MarkAsComplete();
        return e;
    }
}
