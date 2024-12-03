package userinterfaces.playable.wordgames.games;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import wordgames.games.Wordle;

public final class WordleInterface extends userinterfaces.playable.wordgames.GenericWordGameInterface {
    private static Wordle wordle;

    private final String ROW_ONE = "QWERTYUIOP";
    private final String ROW_TWO = "ASDFGHJKL";
    private final String ROW_THREE = "ZXCVBNM";
    private final String TOTAL = ROW_ONE + ROW_TWO + ROW_THREE;

    private void guess() {
        if (guess.size() != letterCount || !wordle.wordList.contains(String.join("", guess))) {
            return;
        }

        wordle.turn(String.join("", guess));

        for (int col = 0; col < letterCount; col++) {
            Rectangle square = (Rectangle) currentScene.lookup("#r" + col + "-" + wordle.guessNum);
            
            int state = wordle.clues[col];
            if (state == 2) {
                square.setFill(Color.LIGHTGREEN);
            } else if (state == 1) {
                square.setFill(Color.YELLOW);
            } else {
                square.setFill(Color.LIGHTGRAY);

                Button letterButton = (Button) currentScene.lookup("#" + guess.get(col));
                letterButton.setStyle("-fx-base: lightgray");
            }
        }

        guess = new ArrayList<>(letterCount);
        if (++wordle.guessNum == letterCount + 1) {
            endGame(wordle);
        }
    }

    private void delete() {
        if (guess.size() > 0) {
            Label letterLabel = (Label) currentScene.lookup("#l" + (guess.size() - 1) + "-" + wordle.guessNum);
            letterLabel.setText("");

            guess.remove(guess.size() - 1);
        }
    }

    private void clear() {
        guess = new ArrayList<>(letterCount);

        for (int i = 0; i < letterCount; i++) {
            Label letterLabel = (Label) currentScene.lookup("#l" + i + "-" + wordle.guessNum);
            letterLabel.setText("");
        }
    }

    private void keyPress(String letter) {
        if (guess.size() < letterCount) {
            guess.add(letter);
            
            Label letterLabel = (Label) currentScene.lookup("#l" + (guess.size() - 1) + "-" + wordle.guessNum);
            letterLabel.setText(letter);
        }
    }

    public WordleInterface() {
        wordle = new Wordle();
        guess = new ArrayList<>(letterCount);

        Label promptLabel = new Label("WORDLE");

        Label timeLabel = new Label();
        timeLabel.setId("time");

        VBox gridBox = new VBox();
        for (int row = 0; row < letterCount + 1; row++) {
            HBox rowBox = new HBox();

            for (int col = 0; col < letterCount; col++) {
                //Letter labels
                Label letterLabel = new Label();
                letterLabel.setId("l" + col + "-" + row);

                Rectangle square = new Rectangle();
                square.setId("r" + col + "-" + row);
                square.setFill(Color.WHITE);
                square.setStroke(Color.GRAY);
                square.setStrokeWidth(2);
                square.setWidth(60);
                square.setHeight(80);
                square.setArcWidth(10);
                square.setArcHeight(10);
                
                StackPane squarePane = new StackPane();
                squarePane.getChildren().addAll(square, letterLabel);

                rowBox.getChildren().add(squarePane);
            }

            gridBox.getChildren().add(rowBox);
        }

        HBox rowOneBox = new HBox();

        for (int i = 0; i < ROW_ONE.length(); i++) {
            String letter = Character.toString(ROW_ONE.charAt(i));
            
            Button keyButton = new Button(letter);
            keyButton.setId(letter);
            keyButton.setFocusTraversable(false);
            keyButton.setOnAction(event -> keyPress(letter));
            
            rowOneBox.getChildren().add(keyButton);
        }

        HBox rowTwoBox = new HBox();

        for (int i = 0; i < ROW_TWO.length(); i++) {
            String letter = Character.toString(ROW_TWO.charAt(i));
            
            Button keyButton = new Button(letter);
            keyButton.setId(letter);
            keyButton.setFocusTraversable(false);
            keyButton.setOnAction(event -> keyPress(letter));

            rowTwoBox.getChildren().add(keyButton);
        }

        HBox rowThreeBox = new HBox();

        for (int i = 0; i < ROW_THREE.length(); i++) {
            String letter = Character.toString(ROW_THREE.charAt(i));
            
            Button keyButton = new Button(letter);
            keyButton.setId(letter);
            keyButton.setFocusTraversable(false);
            keyButton.setOnAction(event -> keyPress(letter));

            rowThreeBox.getChildren().add(keyButton);
        }

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

        Label statusLabel = new Label("Type or click to play a word!");
        statusLabel.setId("status");

        HBox endGameBox = new HBox();

        Button quitButton = new Button("Leave Game");
        quitButton.setId("endgame");
        quitButton.setFocusTraversable(false);
        quitButton.setOnAction(evt -> endGame(wordle));

        Button endGameButton = new Button("End Game");
        endGameButton.setId("endgame");
        endGameButton.setFocusTraversable(false);
        endGameButton.setOnAction(evt -> endGame(wordle));

        endGameBox.getChildren().add(endGameButton);
        
        VBox screen = new VBox();
        screen.getChildren().addAll(promptLabel, timeLabel, gridBox, rowOneBox, rowTwoBox, rowThreeBox, inputBox, statusLabel, endGameBox);

        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        Scene wordleScene = new Scene(layout);
        stage.setScene(wordleScene);

        if (totalTime != 0) {
            wordle.startTimer();
        }

        wordleScene.setOnKeyPressed(evt -> {
            if (TOTAL.contains(evt.getCode().toString())) {
                Button letterButton = (Button) wordleScene.lookup("#" + evt.getCode().toString());
                letterButton.fire();
            } else if (evt.getCode() == KeyCode.ENTER) {
                Button letterButton = (Button) wordleScene.lookup("#enter");
                letterButton.fire();
            } else if (evt.getCode() == KeyCode.BACK_SPACE) {
                Button letterButton = (Button) wordleScene.lookup("#delete");
                letterButton.fire();
            } else if (evt.getCode() == KeyCode.SPACE) {
                Button letterButton = (Button) wordleScene.lookup("#clear");
                letterButton.fire();
            }
        });
    }
}
