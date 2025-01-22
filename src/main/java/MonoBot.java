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
        if (input.equals("bye")) {
            this.StopBot();
            return;
        }
        if (input.equals("list")) {
            this.PrintTaskList();
            return;
        }
        String[] split = input.split(" ", 2);
        String cmd = split[0];
        switch (cmd) {
            case "add":
                String name = split[1];
                this.AddTask(new Task(name));
                this.printMessage("added: " + name);
                break;
            case "mark":
                int idx = Integer.parseInt(split[1]);
                this.MarkTaskComplete(idx);
                break;
            case "unmark":
                int task_idx = Integer.parseInt(split[1]);
                this.UnmarkCompletedTask(task_idx);
                break;
            default:
                this.printMessage("Unknown Command :o");
                break;
        }
    }

    private void MarkTaskComplete(int idx) {
        if (idx > this.tasklist.size()) {
            this.printErrorMessage("Task " + idx + " does not exist! Enter 'list' to view list of tasks.");
            return;
        }
        if (!this.tasklist.get(idx - 1).MarkAsComplete())
            this.printErrorMessage("Task " + idx + " is already completed!");
        else
            this.printMessage("Task " + idx + " has been marked complete!");
    }
    private void UnmarkCompletedTask(int idx) {
        if (idx > this.tasklist.size()) {
            this.printErrorMessage("Task " + idx + " does not exist! Enter 'list' to view list of tasks.");
            return;
        }
        if (!this.tasklist.get(idx - 1).UnmarkCompleted())
             this.printErrorMessage("Task " + idx + " has not been completed!");
        else
            this.printMessage("Task " + idx + " has been unmarked!");
    }

    private void PrintTaskList() {
        String[] list = new String[this.tasklist.size()];
        for (int i = 0; i < this.tasklist.size(); i++)
            list[i] = String.format("%d. %s", i + 1, tasklist.get(i).toString());
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

    private void printErrorMessage(String context) {
        this.printMessage("\\(T o T)'/ " + context);
    }

    private void printMessage(String[] msg) {
        System.out.println(this.INDENT + this.SEPARATOR);
        for (String s : msg)
            System.out.println(this.INDENT + s);
        System.out.println(this.INDENT + this.SEPARATOR);
    }
    
}
