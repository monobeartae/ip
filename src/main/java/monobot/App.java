package monobot;

public class App {
    public void run() {
        UserInputHandler inputHandler = new UserInputHandler();

        MonoBot bot = new MonoBot();
        bot.StartBot();

        while (bot.IsRunning()) {
            String input = inputHandler.getUserInput();
            bot.ProcessInput(input);
        }
    }
}
