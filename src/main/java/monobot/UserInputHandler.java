package monobot;

import java.io.IOException;
import java.util.Scanner;

public class UserInputHandler {
    private Scanner sc;
    private InputType inputType;

    enum InputType {
        Manual,
        Auto
    }

    public UserInputHandler() {
        sc = new Scanner(System.in);
        try {
            inputType = System.in.available() > 0 ? InputType.Auto : InputType.Manual;
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    public String getUserInput() {
        System.out.print("Input: ");
        String input = sc.nextLine();
        if (inputType == InputType.Auto)
            System.out.println();
        return input;
    }
}
