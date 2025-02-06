package app.commands;

/**
 * Represents a command which can be understood by MonoBot
 */
public class Command {
    private CommandType type = CommandType.None;
    private static Command nullCommand = null;

    public Command(CommandType type) {
        this.type = type;
    }

    public CommandType getType() {
        return this.type;
    }

    public static Command getNullCommand() {
        if (Command.nullCommand == null) {
            Command.nullCommand = new Command(CommandType.None);
        }
        return Command.nullCommand;
    }
}
