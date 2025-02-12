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

public class GuiDialogueBox extends HBox {
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
