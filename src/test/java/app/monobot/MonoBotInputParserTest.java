package app.monobot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import app.commands.Command;
import app.commands.CommandType;
import app.commands.StringCommand;
import app.commands.TaskCommand;
import app.commands.TaskIndexCommand;
import app.exceptions.MonoBotException;
import app.tasks.Deadline;
import app.tasks.Event;
import app.tasks.Todo;
import app.utility.DateTime;

public class MonoBotInputParserTest {
    @Test 
    public void addTodoCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("todo cry");
        assertEquals(cmd.getType(), CommandType.AddTask);
        assertEquals(cmd.getClass(), TaskCommand.class);
        TaskCommand tcmd = (TaskCommand)cmd;
        assertEquals(tcmd.getTask().toString(), new Todo("cry").toString());
    }

    @Test 
    public void addDeadlineCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("deadline cry /by 1/1/2025 1600");
        assertEquals(cmd.getType(), CommandType.AddTask);
        assertEquals(cmd.getClass(), TaskCommand.class);
        TaskCommand tcmd = (TaskCommand)cmd;
        assertEquals(tcmd.getTask().toString(), new Deadline("cry", new DateTime("1/1/2025 1600")).toString());
    }

    @Test 
    public void addEventCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("event cry /from 1/1/2025 0000 /to 30/1/2025 2359");
        assertEquals(cmd.getType(), CommandType.AddTask);
        assertEquals(cmd.getClass(), TaskCommand.class);
        TaskCommand tcmd = (TaskCommand)cmd;
        assertEquals(tcmd.getTask().toString(), new Event("cry", new DateTime("1/1/2025 0000"), 
                new DateTime("30/1/2025 2359")).toString());
    }

    @Test 
    public void deleteTaskCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("delete 2");
        assertEquals(cmd.getType(), CommandType.DeleteTask);
        assertEquals(cmd.getClass(), TaskIndexCommand.class);
        TaskIndexCommand tcmd = (TaskIndexCommand)cmd;
        assertEquals(tcmd.getIndex(), 2);
    }

    @Test 
    public void markTaskCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("mark 1");
        assertEquals(cmd.getType(), CommandType.MarkTask);
        assertEquals(cmd.getClass(), TaskIndexCommand.class);
        TaskIndexCommand tcmd = (TaskIndexCommand)cmd;
        assertEquals(tcmd.getIndex(), 1);
    }

    @Test 
    public void unmarkTaskCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("unmark 1");
        assertEquals(cmd.getType(), CommandType.UnmarkTask);
        assertEquals(cmd.getClass(), TaskIndexCommand.class);
        TaskIndexCommand tcmd = (TaskIndexCommand)cmd;
        assertEquals(tcmd.getIndex(), 1);
    }

    @Test 
    public void printTasklistCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("list");
        assertEquals(cmd.getType(), CommandType.PrintTasklist);
        assertEquals(cmd.getClass(), Command.class);
    }

    @Test 
    public void findCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("find cry");
        assertEquals(cmd.getType(), CommandType.PrintFindTasklist);
        assertEquals(cmd.getClass(), StringCommand.class);
        StringCommand tcmd = (StringCommand)cmd;
        assertEquals(tcmd.getKeyword(), "cry");
    }

    @Test 
    public void exitCommandTest() throws MonoBotException {
        MonoBotInputParser parser = new MonoBotInputParser();
        Command cmd = parser.processInput("bye");
        assertEquals(cmd.getType(), CommandType.Exit);
        assertEquals(cmd.getClass(), Command.class);
    }
}
