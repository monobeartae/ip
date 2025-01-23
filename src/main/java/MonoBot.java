import java.util.ArrayList;
import tasks.*;

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
        printMessage("Goodbye :( See you again soon!");
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
            case "todo":
                String td_name = split[1];
                this.AddTask(new Todo(td_name));
                break;
             case "event":
                String[] e_split = split[1].split(" /from ");
                String e_name = e_split[0];
                String[] e_split_2 = e_split[1].split(" /to ");
                String from = e_split_2[0];
                String to = e_split_2[1];
                this.AddTask(new Event(e_name, from, to));
                break;
             case "deadline":
                String[] d_split = split[1].split(" /by ");
                String d_name = d_split[0];
                String deadline = d_split[1];
                this.AddTask(new Deadline(d_name, deadline));
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
                this.printErrorMessage("Unknown Command :o");
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
        String[] msg = new String[] {
            "Got it! I've added this task for you:",
            "  " + task.toString(),
            "Now you have " + this.tasklist.size() + " task(s) in your list :D"
        };
        this.printMessage(msg);
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
