package app.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class GuiDialogueBox extends HBox {
    private static final String BOT_COLOR_HEX = "#F8F8FF";
    private static final String USER_COLOR_HEX = "#B0C4DE";

    @FXML
    private Label dialogueText = null;
    @FXML
    private ImageView displayPicture = null;


    private GuiDialogueBox(String text, Image icon) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GuiChatWindow.class.getResource("/view/DialogueBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.dialogueText.setText(text);
        this.displayPicture.setImage(icon);

        this.maskDisplayImageCircle();
    }

    /**
     * Masks the display picture so it shows as a Circle instead of Square
     */
    private void maskDisplayImageCircle() {
        Circle clip = new Circle();
        clip.setCenterX(this.displayPicture.getFitWidth() / 2);
        clip.setCenterY(this.displayPicture.getFitHeight() / 2);
        clip.setRadius(this.displayPicture.getFitWidth() / 2);
        this.displayPicture.setClip(clip);

    }

    /**
     * Returns a left-aligned dialogue box
     * @param text
     * @param icon
     * @return
     */
    public static GuiDialogueBox getLeftDialogueBox(String text, Image icon) {
        GuiDialogueBox db = new GuiDialogueBox(text, icon);
        db.flip();
        db.dialogueText.setStyle(String.format("-fx-background-color: %s;", GuiDialogueBox.BOT_COLOR_HEX));
        return db;
    }

    /**
     * Returns a right-aligned dialogue box
     * @param text
     * @param icon
     * @return
     */
    public static GuiDialogueBox getRightDialogueBox(String text, Image icon) {
        GuiDialogueBox db = new GuiDialogueBox(text, icon);
        db.dialogueText.setStyle(String.format("-fx-background-color: %s;", GuiDialogueBox.USER_COLOR_HEX));
        return db;
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }
}
