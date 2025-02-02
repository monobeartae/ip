package app.commands;

public class StringCommand extends Command {
    private String keyword;

    public StringCommand(CommandType type, String s) {
        super(type);
        this.keyword = s;
    }

    public String getKeyword() {
        return this.keyword;
    }

}
