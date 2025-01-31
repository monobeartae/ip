package app;

import app.monobot.MonoBot;
import app.monobot.MonoBotInputParser;
import app.monobot.MonoBotUIHandler;

public class App {
    public void run() {
        UserInputHandler inputHandler = new UserInputHandler();

        MonoBot bot = new MonoBot();
        MonoBotInputParser inputParser = new MonoBotInputParser(bot);
        MonoBotUIHandler uiHandler = new MonoBotUIHandler(bot);
        bot.StartBot();

        while (bot.IsRunning()) {
            String input = inputHandler.getUserInput();
            inputParser.ProcessInput(input);
        }
    }
}
