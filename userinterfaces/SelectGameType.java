package userinterfaces;

import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import userinterfaces.playable.WordGameOptionsInterface;

public class SelectGameType extends userinterfaces.UserInterface {
    public static char gameCategory;
    public static String gameName;

    protected void setGame(String gameType) {
        gameName = gameType;

        new WordGameOptionsInterface();
    }
}

final class SelectStrategyGame extends SelectGameType {
    public SelectStrategyGame() {
        gameCategory = 's';
    }
}

final class SelectWordGame extends SelectGameType {
    public SelectWordGame() {
        gameCategory = 'w';

        Label promptLabel = new Label("What game would you like to play?");

        Button anagramsButton = new Button("ANAGRAMS");
        anagramsButton.setMinSize(60, 60);
        anagramsButton.setOnAction(event -> setGame("anagrams"));

        Button wordleButton = new Button("WORDLE");
        wordleButton.setMinSize(60, 60);
        wordleButton.setOnAction(event -> setGame("wordle"));

        Button pangramsButton = new Button("PANGRAMS");
        pangramsButton.setMinSize(60, 60);
        pangramsButton.setOnAction(event -> setGame("pangrams"));

        Button backButton = new Button("BACK");
        backButton.setMinSize(60, 60);
        backButton.setOnAction(event -> stage.setScene(homeScene));

        VBox screen = new VBox(5);

        HBox buttonColumns = new HBox();
        buttonColumns.getChildren().addAll(anagramsButton, wordleButton, pangramsButton);

        screen.getChildren().addAll(promptLabel, buttonColumns, backButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        Scene wordGameScene = new Scene(layout);
        
        stage.setScene(wordGameScene);
    }
}
