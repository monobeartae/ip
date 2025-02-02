package app;

import app.commands.Command;
import app.exceptions.MonoBotException;
import app.exceptions.MonoBotRuntimeException;
import app.monobot.MonoBot;
import app.monobot.MonoBotInputParser;
import app.monobot.MonoBotUIHandler;

public class App {
    public void run() {
        UserInputHandler inputHandler = new UserInputHandler();

        MonoBot bot = new MonoBot();
        MonoBotInputParser inputParser = new MonoBotInputParser();
        MonoBotUIHandler uiHandler = new MonoBotUIHandler(bot);
        bot.startBot();

        while (bot.isRunning()) {
            String input = inputHandler.getUserInput();
            try {
                Command cmd = inputParser.processInput(input);
                bot.processCommand(cmd);
            } catch (MonoBotRuntimeException e) {
                System.out.println(e.getMessage());
            } catch (MonoBotException e) {
                uiHandler.printErrorMessage(e.getMessage());
            }
        }
    }
}
