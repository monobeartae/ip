package monobot;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import monobot.tasks.Deadline;
import monobot.tasks.Event;
import monobot.tasks.Task;
import monobot.tasks.Todo;
import monobot.utility.DateTime;

public class MonoBot {
    private final String INDENT = "   ";
    private final String SEPARATOR = "________________\n";


    private boolean isRunning = false;

    private ArrayList<Task> tasklist = null;
    private SaveHandler saveHandler = null;
    

    public MonoBot() {
       
    }

    public void StartBot() {
        this.isRunning = true;
        this.saveHandler = new SaveHandler();
        this.tasklist = saveHandler.LoadTasks();
        String[] welcomeMessages = new String[] {
            "Hi There! I'm Mono. What can I do for you today?",
            "",
            "list - view tasklist",
            "todo <task_name> - add a todo task",
            "deadline <task_name> /by <deadline> - add a deadline task",
            "event <task_name> /from <start> /to <end> - add an event task",
            "mark <task_number> - mark a task complete",
            "unmark <task_number> - unmark a completed task",
            "delete <task_number> - delete a task"

        };
        printMessage(welcomeMessages);
    }
    
    public boolean IsRunning() { 
        return this.isRunning; 
    }


    private void StopBot() {
        printMessage("Goodbye :( See you again soon!");
        this.saveHandler.SaveTasks(this.tasklist);
        this.isRunning = false;
    }

    public void ProcessInput(String input) {
        if (input.equals("bye")) {
            this.StopBot();
            return;
        }
        if (input.equals("list")) {
            this.PrintTaskList();
            return;
        }
        if (input.contains("|")) {
            this.printErrorMessage("Please don't use '|'! It's my special character, MINE!");
            return;
        }
        String[] split = input.split(" ", 2);
        String cmd = split[0];
        switch (cmd) {
            case "todo":
                this.ProcessTodoInput(split);
                break;
            case "event":
                this.ProcessEventInput(split);
                break;
            case "deadline":
                this.ProcessDeadlineInput(split);
                break;
            case "delete":
                this.ProcessDeleteInput(split);
                break;
            case "mark":
                this.ProcessMarkInput(split);
                break;
            case "unmark":
                this.ProcessUnmarkInput(split);
                break;
            case "find":
                this.ProcessFindInput(split);
                break;
            default:
                this.printErrorMessage("Unknown Command! :o");
                break;
        }
    }

    private void ProcessFindInput(String[] split) {
        if (split.length < 2) {
            this.printErrorMessage("Missing find search keyword! :o");
            return;
        }
        this.PrintFindTaskList(split[1]);

    }

    private void ProcessTodoInput(String[] split) {
        if (split.length < 2) {
            this.printErrorMessage("Todo description is empty! :o");
            return;
        }
        String td_name = split[1];
        this.AddTask(new Todo(td_name));
    }

    private void ProcessEventInput(String[] split) {
        if (split.length < 2) {
            this.printErrorMessage("Event description is empty! :o");
            return;
        }
        if (!split[1].contains(" /from ") || !split[1].contains(" /to ")) {
            this.printErrorMessage("Even if you don't want to go, you have to set the event details! :o");
            return;
        }
        String[] e_split = split[1].split(" /from ");
        String e_name = e_split[0];
        if (e_split.length < 2) {
            this.printErrorMessage("Even if you don't want to go, you have to set the start date! :o");
            return;
        }
        String[] e_split_2 = e_split[1].split(" /to ");
        if (e_split_2.length < 2) {
            this.printErrorMessage("Even if you don't want it to end, you have to set the end date! :o");
            return;
        }
        String from = e_split_2[0];
        String to = e_split_2[1];
        try {
            DateTime start = new DateTime(from);
            DateTime end = new DateTime(to);
            this.AddTask(new Event(e_name, start, end));
        } catch (DateTimeParseException e) {
            this.printErrorMessage(e.getLocalizedMessage());
        }
    }

    private void ProcessDeadlineInput(String[] split) {
        if (split.length < 2) {
            this.printErrorMessage("Deadline description is empty! :o");
            return;
        }
        if (!split[1].contains(" /by ")) {
            this.printErrorMessage("Even if you don't want to do it, you have to set the deadline! :o");
            return;
        }
        String[] d_split = split[1].split(" /by ");
        String d_name = d_split[0];
        if (d_split.length < 2) {
            this.printErrorMessage("Even if you don't want to do it, you have to set the deadline! :o");
            return;
        }
        try {
            String deadline = d_split[1];
            this.AddTask(new Deadline(d_name, new DateTime(deadline)));
        } catch (DateTimeParseException e) {
            this.printErrorMessage(e.getLocalizedMessage());
        }
    }

    private void ProcessMarkInput(String[] split) {
        if (split.length < 2) {
            this.printErrorMessage("Which task did you want to mark? I didn't quite catch that! :o");
            return;
        }
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            this.printErrorMessage("Do you perhaps not know what a number is? :o");
            return;
        }
        this.MarkTaskComplete(idx); 
    }
    
    private void ProcessUnmarkInput(String[] split) {
        if (split.length < 2) {
            this.printErrorMessage("Which task did you want to unmark? I didn't quite catch that! :o");
            return;
        }
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            this.printErrorMessage("Do you perhaps not know what a number is? :o");
            return;
        }
        this.UnmarkCompletedTask(idx);
    }
    private void ProcessDeleteInput(String[] split) {
        if (split.length < 2) {
            this.printErrorMessage("Which task did you want to delete? I didn't quite catch that! :o");
            return;
        }
        int idx;
        try {
            idx = Integer.parseInt(split[1]);
        } catch (java.lang.NumberFormatException e) {
            this.printErrorMessage("Do you perhaps not know what a number is? :o");
            return;
        }
        this.DeleteTask(idx);
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
        if (this.tasklist.isEmpty()) {
            this.printMessage("You have no tasks!");
            return;
        }
        String[] list = new String[this.tasklist.size()];
        for (int i = 0; i < this.tasklist.size(); i++)
            list[i] = String.format("%d. %s", i + 1, tasklist.get(i).toString());
        this.printMessage(list);
    }

    private void PrintFindTaskList(String keyword) {
        if (this.tasklist.isEmpty()) {
            this.printMessage("You have no tasks!");
            return;
        }
        ArrayList<String> msg = new ArrayList<>();
        for (int i = 0; i < this.tasklist.size(); i++) {
            if (!this.tasklist.get(i).MatchName(keyword)) {
                continue;
            }
            msg.add(String.format("%d. %s", i + 1, tasklist.get(i).toString()));
        }
        if (msg.isEmpty()) {
            this.printMessage("You have no tasks!");
            return;
        }
        this.printMessage(msg);
    }
    
    private void AddTask(Task task) {
        this.tasklist.add(task);
        String[] msg = new String[] {
            "Got it! I've added this task for you:",
            "-> " + task.toString(),
            "Now you have " + this.tasklist.size() + " task(s) in your list :D"
        };
        this.printMessage(msg);
    }
    
    private void DeleteTask(int task_num) {
        if (task_num > this.tasklist.size()) {
            this.printErrorMessage("Task " + task_num + " does not exist! Enter 'list' to view list of tasks.");
            return;
        }
        String[] msg = new String[] {
            "Got it! I've removed this task for you:",
            "-> " + this.tasklist.get(task_num - 1).toString(),
            "Now you have " + (this.tasklist.size() - 1) + " task(s) in your list :D"
        };
        this.printMessage(msg);
        this.tasklist.remove(task_num - 1);
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

    private void printMessage(ArrayList<String> msg) {
        System.out.println(this.INDENT + this.SEPARATOR);
        for (String s : msg)
            System.out.println(this.INDENT + s);
        System.out.println(this.INDENT + this.SEPARATOR);
    }
    
}
