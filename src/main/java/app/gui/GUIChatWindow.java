package app.gui;

import java.util.ArrayList;

import app.events.GUIEventListener;
import app.events.GUIEventSource;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GUIChatWindow extends AnchorPane implements GUIEventSource {
    @FXML
    private ScrollPane scrollPane= null;
    @FXML
    private VBox dialogContainer = null;
    @FXML
    private TextField userInput = null;
    @FXML
    private Button sendButton = null;


    private ArrayList<GUIEventListener> listeners = null;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/kanae_roto.jpg"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/kanae.png"));

    public GUIChatWindow() {
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

    public void attachListener(GUIEventListener listener) {
        this.listeners.add(listener);
    }

    private void invokeUserInputEvent(String input) {
        for (GUIEventListener listener : this.listeners) {
            listener.onUserInputEvent(input);
        }
    }

    /**
     * Adds a user's dialogue box to the chat window
     * @param text
     */
    public void addUserDialogue(String text) {
        this.dialogContainer.getChildren().addAll(
            GUIDialogueBox.getRightDialogueBox(text, this.userImage));
    }

    /**
     * Adds MonoBot's dialogue box to the chat window
     * @param text
     */
    public void addBotDialogue(String text) {
        this.dialogContainer.getChildren().addAll(
            GUIDialogueBox.getLeftDialogueBox(text, this.botImage));
    }
}
