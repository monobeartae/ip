import java.util.ArrayList;
import tasks.Task;

public class MonoBot {
    private final String INDENT = "   ";
    private final String SEPARATOR = "________________\n";


    private boolean isRunning;

    private ArrayList<Task> tasklist;
    

    public MonoBot() {
        this.isRunning = false;
        this.tasklist = new ArrayList<>();
    }

    public void StartBot() {
        this.isRunning = true;
        printMessage("Hi There! I'm Mono. What can I do for you today?");
    }
    
    public boolean IsRunning() { 
        return this.isRunning; 
    }


    private void StopBot() {
        printMessage("Goodbye :(");
        this.isRunning = false;
    }

    public void processInput(String input) {
        switch (input) {
            case "bye":
                this.StopBot();
                break;
            case "list":
                this.PrintTaskList();
                break;
            default:
                this.AddTask(new Task(input));
                this.printMessage("added: " + input);
                break;
        }
    }

    private void PrintTaskList() {
        String[] list = new String[this.tasklist.size()];
        for (int i = 0; i < this.tasklist.size(); i++)
            list[i] = String.format("%d. %s", i, tasklist.get(i).toString());
        this.printMessage(list);
    }

    private void AddTask(Task task) {
        this.tasklist.add(task);
    }


    private void printMessage(String msg) {
        System.out.println(this.INDENT + this.SEPARATOR);
        System.out.println(this.INDENT + msg);
        System.out.println(this.INDENT + this.SEPARATOR);
    }

    private void printMessage(String[] msg) {
        System.out.println(this.INDENT + this.SEPARATOR);
        for (String s : msg)
            System.out.println(this.INDENT + s);
        System.out.println(this.INDENT + this.SEPARATOR);
    }
    
}
