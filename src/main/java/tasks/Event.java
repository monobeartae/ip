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
        return super.toString() + String.format("(from: %s to: %s)", this.start, this.end);
    }
}
