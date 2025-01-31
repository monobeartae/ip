package app.tasks;

public class Todo extends Task{
    public Todo(String name) {
        super(name);
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String EncodeTask() {
        return "T|" + super.EncodeTask();
    }

    public static Todo Decode(String line) {
        String[] split = line.split("\\|");
        if (split.length != 3) {
            System.out.println("Todo: Could not decode '" + line + "'. PLease check the format.");
            return null;
        }
        Todo todo = new Todo(split[1]);
        if (split[2].equals("true"))
            todo.MarkAsComplete();
        return todo;
    }
}
