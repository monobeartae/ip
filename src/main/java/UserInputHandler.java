import java.util.Scanner;

public class UserInputHandler {
    private Scanner sc;

    public UserInputHandler() {
        sc = new Scanner(System.in);
    }
    public String getUserInput() {
        System.out.print("Input: ");
        String input = sc.nextLine();
        return input;
    }
}
