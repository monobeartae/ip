public class MonoBot {
    private final String INDENT = "   ";
    private final String SEPARATOR = "________________\n";


    private boolean isRunning;
    

    public MonoBot() {
        this.isRunning = false;
    }

    public void StartBot() {
        this.isRunning = true;
        printMessage("Hi There! I'm Mono. What can I do for you today?");
    }

    public void processInput(String input) {
        switch (input) {
            case "bye":
                this.StopBot();
                break;
            default:
                this.printMessage(input);
                break;
        }
    }

    public boolean IsRunning() { 
        return this.isRunning; 
    }

    private void StopBot() {
        printMessage("Goodbye :(");
        this.isRunning = false;
    }

    private void printMessage(String msg) {
        System.out.println(this.INDENT + this.SEPARATOR);
        System.out.println(this.INDENT + msg);
        System.out.println(this.INDENT + this.SEPARATOR);
    }
    
}
