package userinterfaces.playable.wordgames.games;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import wordgames.games.Pangrams;

public final class PangramsInterface extends userinterfaces.playable.wordgames.GenericWordGameInterface {
    private static Pangrams pangrams;

    static void buttonPress(Button letterButton) {
        guess.add(letterButton.getId());

        Label letterLabel = (Label) currentScene.lookup("#input");
        letterLabel.setText(letterLabel.getText() + guess.get(guess.size() - 1));
    }

    static void guess() {
        String guessStr = String.join("", guess);
        String output = pangrams.turn(guessStr);

        if (output.equals(guessStr + "! AMAZING!")) {
            if (showHints) {
                Label hintLabel = (Label) currentScene.lookup("#hint" + Integer.toString(guessStr.length() - 3));
                String currentHint = hintLabel.getText();
                int count = Integer.parseInt(currentHint.replace(guessStr.length() + " letter words left: ", ""));
                hintLabel.setText(guessStr.length() + " letter words left: " + --count);
            }

            for (Character c : pangrams.answer.toCharArray()) {
                Button letterButton = (Button) currentScene.lookup("#" + c.toString());
                letterButton.setStyle("-fx-base: lightgreen");
            }
        } else if (output.equals(guessStr + "! Great!") && showHints) {
            Label hintLabel = (Label) currentScene.lookup("#hint" + Integer.toString(guessStr.length() - 3));
            String currentHint = hintLabel.getText();
            int count = Integer.parseInt(currentHint.replace(guessStr.length() + " letter words left: ", ""));
            hintLabel.setText(guessStr.length() + " letter words left: " + --count);
        }

        Label statusLabel = (Label) currentScene.lookup("#status");
        statusLabel.setText(output);

        Label pointsLabel = (Label) currentScene.lookup("#points");
        pointsLabel.setText(Integer.toString(pangrams.points));

        clear();
    }

    static void delete() {
        if (guess.size() == 0) {
            return;
        }

        Label letterLabel = (Label) currentScene.lookup("#input");
        guess.remove(guess.size() - 1);
        letterLabel.setText(String.join("", guess));
    }

    static void shuffle() {
        guess = new ArrayList<String>();

        String oldString = pangrams.answer;
        pangrams.shuffle();

        for (int i = 0; i < pangrams.answer.length(); i++) {
            Label letterLabel = (Label) currentScene.lookup("#input");
            letterLabel.setText("");

            String letter = Character.toString(oldString.charAt(i));
            Button letterButton = (Button) currentScene.lookup("#" + letter);
            letterButton.setText(Character.toString(pangrams.answer.charAt(i)));
            letterButton.setId(Character.toString(pangrams.answer.charAt(i)));
        }
    }

    static void clear() {
        guess = new ArrayList<String>();

        Label letterLabel = (Label) currentScene.lookup("#input");
        letterLabel.setText("");
    }

    public static void back(Scene previousScene) {
        if (pangrams.timer != null) {
            pangrams.timer.cancel();
        }

        stage.setScene(previousScene);
    }

    public PangramsInterface() {
        pangrams = new Pangrams();

        guess = new ArrayList<String>(letterCount);
        letterTotal = letterCount;

        Label promptLabel = new Label("PANGRAMS");

        HBox infoBox = new HBox(25);
        
        Label pointsLabel = new Label("0");
        pointsLabel.setId("points");

        Label timeLabel = new Label();
        timeLabel.setId("time");

        infoBox.getChildren().addAll(pointsLabel, timeLabel);

        Label letterLabel = new Label();
        letterLabel.setId("input");

        Rectangle square = new Rectangle();
        square.setFill(Color.WHITE);
        square.setStroke(Color.GRAY);
        square.setStrokeWidth(2);
        square.setWidth(400);
        square.setHeight(80);
        square.setArcWidth(10);
        square.setArcHeight(10);

        StackPane squarePane = new StackPane();
        squarePane.getChildren().addAll(square, letterLabel);
        
        HBox buttonBox = new HBox();

        for (String letterStr : pangrams.specialLetterArr) {
            //Letter buttons
            Button letterButton = new Button(letterStr);
            letterButton.setId(letterStr);
            letterButton.setFocusTraversable(false);
            letterButton.setOnAction(event -> buttonPress(letterButton));
            letterButton.setStyle("-fx-base: yellow");
            
            buttonBox.getChildren().add(letterButton);
        }

        for (int i = 0; i < pangrams.answer.length(); i++) {
            //Letter buttons
            String letterStr = Character.toString(pangrams.answer.charAt(i));
            Button letterButton = new Button(letterStr);
            letterButton.setId(letterStr);
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
        endGameButton.setOnAction(evt -> endGame(pangrams));

        endGameBox.getChildren().add(endGameButton);

        gameButtons.getChildren().addAll(shuffleBox, inputBox, endGameBox);

        Label statusLabel = new Label("Type or click to play a word!");
        statusLabel.setId("status");

        Button backButton = new Button("Back");
        backButton.setFocusTraversable(false);
        backButton.setOnAction(evt -> back(previousScene));

        ScrollPane hintPane = new ScrollPane();

        if (showHints) {
            VBox hintBox = new VBox();
            for (int i = 0; i < pangrams.wordOptions.size() - 2; i++) {
                int letters = i + 3;
                
                Label wordCountLabel = new Label();
                wordCountLabel.setText(letters + " letter words left: " + pangrams.wordOptions.get(i).size());
                wordCountLabel.setId("hint" + i);

                hintBox.getChildren().add(wordCountLabel);
            }
            hintPane.setContent(hintBox);
        }

        VBox screen = new VBox(5);
        screen.getChildren().addAll(promptLabel, infoBox, squarePane, buttonBox, gameButtons, statusLabel, backButton, hintPane);

        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        Scene pangramsScene = new Scene(layout);
        pangramsScene.setOnKeyPressed(event -> {
            if (pangrams.answer.contains(event.getCode().toString()) || pangrams.specialLetterArr.contains(event.getCode().toString())) {
                Button letterButton = (Button) pangramsScene.lookup("#" + event.getCode().toString());

                letterButton.fire();
            } else if (event.getCode() == KeyCode.ENTER) {
                Button letterButton = (Button) pangramsScene.lookup("#enter");
                letterButton.fire();
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                Button letterButton = (Button) pangramsScene.lookup("#delete");
                letterButton.fire();
            } else if (event.getCode() == KeyCode.SPACE) {
                Button letterButton = (Button) pangramsScene.lookup("#clear");
                letterButton.fire();
            } else if (event.getCode() == KeyCode.CONTROL) {
                Button letterButton = (Button) pangramsScene.lookup("#shuffle");
                letterButton.fire();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                Button letterButton = (Button) pangramsScene.lookup("#endgame");
                letterButton.fire();
            }
        });

        previousScene = homeScene;
        currentScene = pangramsScene;
        stage.setScene(pangramsScene);

        if (totalTime != 0) {
            pangrams.startTimer();
        }
    }
}
