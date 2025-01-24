public class Main {
    public static void main(String[] args) {
        UserInputHandler inputHandler = new UserInputHandler();

        MonoBot bot = new MonoBot();
        bot.StartBot();

        while (bot.IsRunning()) {
            String input = inputHandler.getUserInput();
            bot.processInput(input);
        }
    }
}
