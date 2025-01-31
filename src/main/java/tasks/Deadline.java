package tasks;

public class Deadline extends Task {
    private String deadline;

    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", this.deadline);
    }

    @Override
    public String EncodeTask() {
        return "D|" + deadline + "|" + super.EncodeTask();
    }

    public static Deadline Decode(String line) {
        String[] split = line.split("\\|");
        if (split.length != 4) {
            System.out.println("Deadline: Could not decode '" + line + "'. PLease check the format.");
            return null;
        }
        Deadline d = new Deadline(split[2], split[1]);
        if (split[3].equals("true"))
            d.MarkAsComplete();
        return d;
    }
}
