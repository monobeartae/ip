package app.commands;

public class TaskIndexCommand extends Command {
    private int index = 0;

    public TaskIndexCommand(CommandType type, int idx) {
        super(type);
        this.index = idx;
    }

    public int getIndex() {
        return index;
    }



}
