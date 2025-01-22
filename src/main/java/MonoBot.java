public class MonoBot {
    private final String SEPARATOR = "\n__________\n\n";

    public void printGreetingMessage() {
        System.out.print(SEPARATOR + "Hi There! I'm Mono. What can I do for you today?"+SEPARATOR);
    }
    public void printExitMessage() {
        System.out.print("Goodbye :(" + SEPARATOR);
    }
    
}
