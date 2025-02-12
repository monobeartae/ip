package app.gui;

import java.util.ArrayList;

import app.events.GuiEventListener;
import app.events.GuiEventSource;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GuiChatWindow extends AnchorPane implements GuiEventSource {
    @FXML
    private ScrollPane scrollPane = null;
    @FXML
    private VBox dialogContainer = null;
    @FXML
    private TextField userInput = null;
    @FXML
    private Button sendButton = null;


    private ArrayList<GuiEventListener> listeners = null;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/kanae_roto.jpg"));
    private final Image botImage = new Image(this.getClass().getResourceAsStream("/images/kanae.png"));

    public GuiChatWindow() {
        this.listeners = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Callback on user input entered
     */
    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        this.invokeUserInputEvent(input);
        this.userInput.clear();
    }

    public void attachListener(GuiEventListener listener) {
        this.listeners.add(listener);
    }

    private void invokeUserInputEvent(String input) {
        for (GuiEventListener listener : this.listeners) {
            listener.onUserInputEvent(input);
        }
    }

    /**
     * Adds a user's dialogue box to the chat window
     * @param text
     */
    public void addUserDialogue(String text) {
        this.dialogContainer.getChildren().addAll(
            GuiDialogueBox.getRightDialogueBox(text, this.userImage));
    }

    /**
     * Adds MonoBot's dialogue box to the chat window
     * @param text
     */
    public void addBotDialogue(String text) {
        this.dialogContainer.getChildren().addAll(
            GuiDialogueBox.getLeftDialogueBox(text, this.botImage));
    }
}
