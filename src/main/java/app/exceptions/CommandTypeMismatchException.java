package app.exceptions;

import app.commands.Command;
import app.commands.CommandType;

public class CommandTypeMismatchException extends MonoBotRuntimeException {
    private Class<?> expectedType = null;
    private Class<?> actualType = null;
    private CommandType command = CommandType.None;

    public <T extends Command, S extends Command> CommandTypeMismatchException(CommandType command,
            Class<T> expected, Class<S> actual) {
        this.command = command;
        this.expectedType = expected;
        this.actualType = actual;
    }

    @Override
    public String getMessage() {
        return String.format(
                "CommandTypeMismatchException: Command '%s' should be of type '%s', but was '%s' instead! :(",
                this.command, this.expectedType.getName(), this.actualType.getName());
    }

}
