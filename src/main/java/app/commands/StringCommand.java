package app.commands;

public class StringCommand extends Command {
    private String keyword;

    public StringCommand(CommandType type, String keyword) {
        super(type);
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

}
