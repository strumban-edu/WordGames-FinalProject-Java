import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import wordgames.games.*;

public class UserInterface extends Application {
    static Label promptLabel;

    static Button[][] buttonArray;
    static Button anagramsButton;
    static Button wordleButton;
    static Button spellingBeeButton;
    static Button quitButton;

    static Scene sceneVar;

    Anagrams anagrams;
    Wordle wordle;
    SpellingBee spellingBee;

    public UserInterface() {
        //
    }

    private void anagrams(int letters) {
        anagrams = new Anagrams(letters);
    }

    private void wordle(int letters) {
        wordle = new Wordle(letters);
    }

    private void spellingBee() {
        spellingBee = new SpellingBee();
    }

    private void gameButton(String id) {
        if (id == "anagrams") {
            //
        } else if (id == "wordle") {
            //
        } else if (id == "spellingbee") {
            //
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        promptLabel = new Label("What game would you like to play?");

        anagramsButton = new Button("ANAGRAMS");
        anagramsButton.setMinSize(60, 60);
        anagramsButton.setId("anagrams");
        anagramsButton.setOnAction(evt -> gameButton(anagramsButton.getId()));

        wordleButton = new Button("WORDLE");
        wordleButton.setMinSize(60, 60);
        wordleButton.setId("wordle");
        wordleButton.setOnAction(evt -> gameButton(wordleButton.getId()));

        spellingBeeButton = new Button("SPELLING BEE");
        spellingBeeButton.setMinSize(60, 60);
        spellingBeeButton.setId("spellingbee");
        spellingBeeButton.setOnAction(evt -> gameButton(spellingBeeButton.getId()));

        quitButton = new Button("QUIT");
        quitButton.setMinSize(60, 60);
        quitButton.setOnAction(event -> {
            Platform.exit();
        });

        VBox screen = new VBox(5);

        HBox buttonColumns = new HBox();
        buttonColumns.getChildren().addAll(anagramsButton, wordleButton, spellingBeeButton);

        screen.getChildren().addAll(promptLabel, buttonColumns, quitButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        primaryStage.setTitle("Word Games");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);

        sceneVar = new Scene(layout);
        primaryStage.setScene(sceneVar);
        primaryStage.show();
    }
}
