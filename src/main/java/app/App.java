package app;

import java.io.IOException;

import app.commands.Command;
import app.events.GUIEventListener;
import app.exceptions.MonoBotException;
import app.exceptions.MonoBotRuntimeException;
import app.gui.GUIChatWindow;
import app.monobot.MonoBot;
import app.monobot.MonoBotGUIHandler;
import app.monobot.MonoBotInputParser;
import app.monobot.MonoBotUIHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class App extends Application implements GUIEventListener {

    private GUIChatWindow chatWindowGui = null;
    private MonoBot bot = null;
    private MonoBotInputParser parser = null;
    private MonoBotGUIHandler guiHandler = null;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setMaxHeight(1080);
            stage.setMaxWidth(1920);
            stage.show();


            this.chatWindowGui = fxmlLoader.<GUIChatWindow>getController();
            this.chatWindowGui.attachListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bot = new MonoBot();
        this.parser = new MonoBotInputParser();
        this.guiHandler = new MonoBotGUIHandler(this.bot, (text) -> {
            this.chatWindowGui.addBotDialogue(text);
        });
        this.bot.startBot();
    }

    @Override
    public void onUserInputEvent(String input) {
        this.chatWindowGui.addUserDialogue(input);
        try {
            Command cmd = this.parser.processInput(input);
            this.bot.processCommand(cmd);
        } catch (MonoBotRuntimeException e) {
            e.printStackTrace();
        } catch (MonoBotException e) {
            this.guiHandler.sendErrorMessage(e.getMessage());
        }
    }




    public void run() {
        UserInputHandler inputHandler = new UserInputHandler();

        MonoBot bot = new MonoBot();
        MonoBotInputParser inputParser = new MonoBotInputParser();
        MonoBotUIHandler uiHandler = new MonoBotUIHandler(bot);
        bot.startBot();

        while (bot.isRunning()) {
            String input = inputHandler.getUserInput();
            try {
                Command cmd = inputParser.processInput(input);
                bot.processCommand(cmd);
            } catch (MonoBotRuntimeException e) {
                System.out.println(e.getMessage());
            } catch (MonoBotException e) {
                uiHandler.printErrorMessage(e.getMessage());
            }
        }
    }
}
