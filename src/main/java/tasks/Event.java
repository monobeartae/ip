package tasks;

public class Event extends Task {

    private String start;
    private String end;
    
    public Event(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (from: %s to: %s)", this.start, this.end);
    }

    @Override
    public String EncodeTask() {
        return "E|" + start + "|" + end + "|" + super.EncodeTask();
    }

    public static Event Decode(String line) {
        String[] split = line.split("\\|");
        if (split.length != 5) {
            System.out.println("Event: Could not decode '" + line + "'. PLease check the format.");
            return null;
        }
        Event e = new Event(split[3], split[1], split[2]);
        if (split[4].equals("true"))
            e.MarkAsComplete();
        return e;
    }
}
