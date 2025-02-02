package app.tasks;

/**
 * Represents a Todo
 */
public class Todo extends Task{
    public Todo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String encodeTask() {
        return "T|" + super.encodeTask();
    }

    /**
     * Decodes a string representing an encoded Todo task object
     * @param line Encoded Todo task as a string
     * @return Todo object
     */
    public static Todo decode(String line) {
        String[] split = line.split("\\|");
        if (split.length != 3) {
            System.out.println("Todo: Could not decode '" + line + "'. PLease check the format.");
            return null;
        }
        Todo todo = new Todo(split[1]);
        if (split[2].equals("true"))
            todo.markAsComplete();
        return todo;
    }
}
