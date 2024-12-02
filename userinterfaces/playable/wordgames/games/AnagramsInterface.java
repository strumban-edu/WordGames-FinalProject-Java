package userinterfaces.playable.wordgames.games;

import java.util.ArrayList;

import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import wordgames.games.Anagrams;

final public class AnagramsInterface extends userinterfaces.playable.wordgames.GenericWordGameInterface {
    private static Anagrams anagrams;

    static void buttonPress(Button letterButton) {
        letterButton.setDisable(true);
        guess.add(Character.toString(letterButton.getId().charAt(0)));

        for (int i = 0; i < guess.size(); i++) {
            Label letterLabel = (Label) currentScene.lookup("#" + Integer.toString(i));
            letterLabel.setText(guess.get(i));
        }
    }

    static void guess() {
        String guessStr = String.join("", guess);
        String output = anagrams.turn(guessStr);
        if (output.equals(guessStr + "! AMAZING!")) {
            for (int i = 0; i < guess.size(); i++) {
                String letter = Character.toString(anagrams.answer.charAt(i));
                Button letterButton = (Button) currentScene.lookup("#" + letter + Integer.toString(i));
                letterButton.setStyle("-fx-base: lightgreen");
            }
        }

        Label statusLabel = (Label) currentScene.lookup("#status");
        statusLabel.setText(output);

        Label pointsLabel = (Label) currentScene.lookup("#points");
        pointsLabel.setText(Integer.toString(anagrams.points));

        clear();
    }

    static void delete() {
        if (guess.size() == 0) {
            return;
        }

        int lastIndex = guess.size() - 1;
        String letter = guess.remove(lastIndex);

        Label letterLabel = (Label) currentScene.lookup("#" + Integer.toString(lastIndex));
        letterLabel.setText("");

        for (int i = 0; i < letterTotal; i++) {
            Button letterButton = (Button) currentScene.lookup("#" + letter + Integer.toString(i));
            if (letterButton == null) {
                continue;
            }

            if (letterButton.isDisable()) {
                letterButton.setDisable(false);
                break;
            }
        }
    }

    static void shuffle() {
        guess = new ArrayList<String>(letterTotal);

        String oldString = anagrams.answer;
        anagrams.shuffle();

        for (int i = 0; i < letterTotal; i++) {
            Label letterLabel = (Label) currentScene.lookup("#" + Integer.toString(i));
            letterLabel.setText("");

            String letter = Character.toString(oldString.charAt(i));
            Button letterButton = (Button) currentScene.lookup("#" + letter + Integer.toString(i));
            letterButton.setText(Character.toString(anagrams.answer.charAt(i)));
            letterButton.setId(Character.toString(anagrams.answer.charAt(i)) + Integer.toString(i));
            letterButton.setDisable(false);
        }
    }

    static void clear() {
        guess = new ArrayList<String>(letterTotal);

        for (int i = 0; i < letterTotal; i++) {
            Label letterLabel = (Label) currentScene.lookup("#" + Integer.toString(i));
            letterLabel.setText("");

            String letter = Character.toString(anagrams.answer.charAt(i));
            Button letterButton = (Button) currentScene.lookup("#" + letter + Integer.toString(i));
            letterButton.setDisable(false);
        }
    }

    public static void back(Scene previousScene) {
        if (anagrams.timer != null) {
            anagrams.timer.cancel();
        }

        stage.setScene(previousScene);
    }

    public AnagramsInterface() {
        anagrams = new Anagrams();

        guess = new ArrayList<String>(letterCount);
        letterTotal = letterCount;

        Label promptLabel = new Label("ANAGRAMS");

        HBox infoBox = new HBox(25);
        
        Label pointsLabel = new Label("0");
        pointsLabel.setId("points");

        Label timeLabel = new Label();
        timeLabel.setId("time");

        infoBox.getChildren().addAll(pointsLabel, timeLabel);

        HBox labelBox = new HBox();
        HBox buttonBox = new HBox();
        for (int i = 0; i < letterCount; i++) {
            //Letter labels
            Label letterLabel = new Label();
            letterLabel.setId(Integer.toString(i));

            Rectangle square = new Rectangle();
            square.setFill(Color.WHITE);
            square.setStroke(Color.GRAY);
            square.setStrokeWidth(2);
            square.setWidth(60);
            square.setHeight(80);
            square.setArcWidth(10);
            square.setArcHeight(10);
            
            StackPane squarePane = new StackPane();
            squarePane.getChildren().addAll(square, letterLabel);

            labelBox.getChildren().add(squarePane);

            //Letter buttons
            String letterStr = Character.toString(anagrams.answer.charAt(i));
            Button letterButton = new Button(letterStr);
            letterButton.setId(letterStr + Integer.toString(i));
            letterButton.setFocusTraversable(false);
            letterButton.setOnAction(event -> buttonPress(letterButton));
            
            buttonBox.getChildren().add(letterButton);
        }

        VBox gameButtons = new VBox(15);

        HBox shuffleBox = new HBox();

        Button shuffleButton = new Button("Shuffle");
        shuffleButton.setId("shuffle");
        shuffleButton.setFocusTraversable(false);
        shuffleButton.setOnAction(evt -> shuffle());

        shuffleBox.getChildren().add(shuffleButton);

        HBox inputBox = new HBox();

        Button enterButton = new Button("Enter");
        enterButton.setId("enter");
        enterButton.setFocusTraversable(false);
        enterButton.setOnAction(evt -> guess());

        Button deleteButton = new Button("Delete");
        deleteButton.setId("delete");
        deleteButton.setFocusTraversable(false);
        deleteButton.setOnAction(evt -> delete());

        Button clearButton = new Button("Clear");
        clearButton.setId("clear");
        clearButton.setFocusTraversable(false);
        clearButton.setOnAction(evt -> clear());

        inputBox.getChildren().addAll(clearButton, deleteButton, enterButton);

        HBox endGameBox = new HBox();

        Button endGameButton = new Button("End Game");
        endGameButton.setId("endgame");
        endGameButton.setFocusTraversable(false);
        endGameButton.setOnAction(evt -> endGame(anagrams));

        endGameBox.getChildren().add(endGameButton);

        gameButtons.getChildren().addAll(shuffleBox, inputBox, endGameBox);

        Label statusLabel = new Label("Type or click to play a word!");
        statusLabel.setId("status");

        Button backButton = new Button("Back");
        backButton.setFocusTraversable(false);
        backButton.setOnAction(evt -> back(previousScene));

        VBox screen = new VBox(5);
        screen.getChildren().addAll(promptLabel, infoBox, labelBox, buttonBox, gameButtons, statusLabel, backButton);

        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        Scene anagramScene = new Scene(layout);
        anagramScene.setOnKeyPressed(evt -> {
            if (anagrams.answer.contains(evt.getCode().toString())) {
                for (int i = 0; i < letterCount; i++) {
                    Button letterButton = (Button) anagramScene.lookup("#" + evt.getCode().toString() + Integer.toString(i));
                    if (letterButton == null) {
                        continue;
                    }

                    if (!letterButton.isDisable()) {
                        letterButton.fire();
                        break;
                    }
                }
            } else if (evt.getCode() == KeyCode.ENTER) {
                Button letterButton = (Button) anagramScene.lookup("#enter");
                letterButton.fire();
            } else if (evt.getCode() == KeyCode.BACK_SPACE) {
                Button letterButton = (Button) anagramScene.lookup("#delete");
                letterButton.fire();
            } else if (evt.getCode() == KeyCode.SPACE) {
                Button letterButton = (Button) anagramScene.lookup("#clear");
                letterButton.fire();
            } else if (evt.getCode() == KeyCode.CONTROL) {
                Button letterButton = (Button) anagramScene.lookup("#shuffle");
                letterButton.fire();
            } else if (evt.getCode() == KeyCode.ESCAPE) {
                Button letterButton = (Button) anagramScene.lookup("#endgame");
                letterButton.fire();
            }
        });

        previousScene = homeScene;
        currentScene = anagramScene;
        stage.setScene(anagramScene);

        if (totalTime != 0) {
            anagrams.startTimer();
        }
    }
}
