package userinterfaces;

import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.shape.Rectangle;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import wordgames.games.*;
import stratgames.*;

public class UserInterface extends Application {
    public static Scene mainScene;

    static Label promptLabel;
    static Button quitButton;

    static Button strategyGamesButton;
    static Button wordGamesButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        promptLabel = new Label("Select a game type:");

        strategyGamesButton = new Button("STRATEGY GAMES");
        strategyGamesButton.setMinSize(60, 60);
        strategyGamesButton.setId("strategy");
        strategyGamesButton.setOnAction(evt -> SelectStrategyGame.show(primaryStage));

        wordGamesButton = new Button("WORD GAMES");
        wordGamesButton.setMinSize(60, 60);
        wordGamesButton.setId("word");
        wordGamesButton.setOnAction(evt -> SelectWordGame.show(primaryStage));

        quitButton = new Button("QUIT");
        quitButton.setMinSize(60, 60);
        quitButton.setOnAction(event -> {
            Platform.exit();
        });

        VBox screen = new VBox(5);

        HBox buttonColumns = new HBox();
        buttonColumns.getChildren().addAll(strategyGamesButton, wordGamesButton);

        screen.getChildren().addAll(promptLabel, buttonColumns, quitButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        mainScene = new Scene(layout);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}

final class SelectStrategyGame {
    public static void show(Stage primaryStage) {
        //
    }
}

final class SelectWordGame {
    static Scene wordGameScene;

    static Label promptLabel;
    static Button backButton;

    static Button anagramsButton;
    static Button wordleButton;
    static Button pangramsButton;

    public static void show(Stage primaryStage) {
        promptLabel = new Label("What game would you like to play?");

        anagramsButton = new Button("ANAGRAMS");
        anagramsButton.setMinSize(60, 60);
        anagramsButton.setId("ANAGRAMS");
        anagramsButton.setOnAction(evt -> WordGameOptions.show(primaryStage, wordGameScene, anagramsButton.getId()));

        wordleButton = new Button("WORDLE");
        wordleButton.setMinSize(60, 60);
        wordleButton.setId("WORDLE");
        wordleButton.setOnAction(evt -> WordGameOptions.show(primaryStage, wordGameScene, wordleButton.getId()));

        pangramsButton = new Button("PANGRAMS");
        pangramsButton.setMinSize(60, 60);
        pangramsButton.setId("PANGRAMS");
        pangramsButton.setOnAction(evt -> WordGameOptions.show(primaryStage, wordGameScene, pangramsButton.getId()));

        backButton = new Button("BACK");
        backButton.setMinSize(60, 60);
        backButton.setOnAction(event -> {
            primaryStage.setScene(UserInterface.mainScene);
        });

        VBox screen = new VBox(5);

        HBox buttonColumns = new HBox();
        buttonColumns.getChildren().addAll(anagramsButton, wordleButton, pangramsButton);

        screen.getChildren().addAll(promptLabel, buttonColumns, backButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        wordGameScene = new Scene(layout);
        primaryStage.setScene(wordGameScene);
    }
}

final class WordGameOptions {
    static Scene wordOptionsScene;

    static final int SECONDS = 1000;
    static final int MINUTES = SECONDS * 60;
    static final int HOURS = MINUTES * 60;

    static String gameName;
    static int letterCount;
    static String timeControlString;

    static int totalTimeSet;
    static int addTimeSet;
    static boolean showHints;

    static Label promptLabel;
    static ToggleGroup letterCountButtons = new ToggleGroup();
    static ToggleGroup timeControlButtons = new ToggleGroup();
    static CheckBox showHintsBox;

    private static void wordGameController(Toggle button, int letters, int totalTime, int addTime, String timeControl) {
        letterCount = letters;
        totalTimeSet = totalTime;
        addTimeSet = addTime * SECONDS;
        timeControlString = timeControl;

        button.setSelected(true);
        promptLabel.setText("Playing " + gameName + ", Letters: " + letters + ", Time Control: " + timeControl);
    }

    private static void startGame(Stage primaryStage) {
        if (gameName.equals("ANAGRAMS")) {
            AnagramsInterface.start(primaryStage, wordOptionsScene, letterCount, totalTimeSet, addTimeSet, showHints);
        } else if (gameName.equals("WORDLE")) {
            WordleInterface.start(primaryStage, wordOptionsScene, letterCount, totalTimeSet, addTimeSet, showHints);
        } else if (gameName.equals("PANGRAMS")) {
            PangramsInterface.start(primaryStage, wordOptionsScene, letterCount, totalTimeSet, addTimeSet, showHints);
        }
    }

    public static void show(Stage primaryStage, Scene previousScene, String gameType) {
        gameName = gameType;

        promptLabel = new Label("Playing " + gameType + ", time control: ,");

        Label letterLabel = new Label("Choose a number of letters");
        HBox letterCountBox = new HBox();
        for (int i = 0; i <= 11; i++) {
            int tempLetterCount = i + 4;
            ToggleButton letterButton = new ToggleButton(Integer.toString(tempLetterCount));
            letterButton.setToggleGroup(letterCountButtons);
            letterButton.setOnAction(evt -> wordGameController(letterButton, tempLetterCount, totalTimeSet, addTimeSet, timeControlString));
            letterCountBox.getChildren().add(letterButton);

            if (gameType.equals("ANAGRAMS") && tempLetterCount == 6) {
                letterButton.fire();
            } else if (gameType.equals("WORDLE") && tempLetterCount == 5) {
                letterButton.fire();
            } else if (gameType.equals("PANGRAMS") && tempLetterCount == 7) {
                letterButton.fire();
            }
        }
        
        Label timeControlLabel = new Label("Choose a time control");
        Label casualLabel = new Label("Casual:");
        ToggleButton casualButton = new ToggleButton("Unlimited");
        casualButton.setToggleGroup(timeControlButtons);
        casualButton.setOnAction(evt -> wordGameController(casualButton, letterCount, 0, 0, "Unlimited"));

        Label bulletLabel = new Label("Bullet:");
        ToggleButton twentySecButton = new ToggleButton("20sec");
        twentySecButton.setOnAction(evt -> wordGameController(twentySecButton, letterCount, 20 * SECONDS, 0, "20sec"));
        twentySecButton.setToggleGroup(timeControlButtons);
        ToggleButton twentySecPlusOneButton = new ToggleButton("20sec+1");
        twentySecPlusOneButton.setOnAction(evt -> wordGameController(twentySecPlusOneButton, letterCount, 20 * SECONDS, 1, "20sec+1"));
        twentySecPlusOneButton.setToggleGroup(timeControlButtons);
        ToggleButton twentySecPlusTwoButton = new ToggleButton("20sec+2");
        twentySecPlusTwoButton.setOnAction(evt -> wordGameController(twentySecPlusTwoButton, letterCount, 20 * SECONDS, 2, "20sec+2"));
        twentySecPlusTwoButton.setToggleGroup(timeControlButtons);
        ToggleButton twentySecPlusFourButton = new ToggleButton("20sec+4");
        twentySecPlusFourButton.setOnAction(evt -> wordGameController(twentySecPlusFourButton, letterCount, 20 * SECONDS, 4, "20sec+4"));
        twentySecPlusFourButton.setToggleGroup(timeControlButtons);

        Label blitzLabel = new Label("Blitz:");
        ToggleButton oneMinButton = new ToggleButton("1min");
        oneMinButton.setToggleGroup(timeControlButtons);
        oneMinButton.setOnAction(evt -> wordGameController(oneMinButton, letterCount, MINUTES, 0, "1min"));
        ToggleButton oneMinPlusThreeButton = new ToggleButton("1min+3");
        oneMinPlusThreeButton.setOnAction(evt -> wordGameController(oneMinPlusThreeButton, letterCount, MINUTES, 3, "1min+3"));
        oneMinPlusThreeButton.setToggleGroup(timeControlButtons);
        ToggleButton oneMinPlusSixButton = new ToggleButton("1min+6");
        oneMinPlusSixButton.setOnAction(evt -> wordGameController(oneMinPlusSixButton, letterCount, MINUTES, 6, "1min+6"));
        oneMinPlusSixButton.setToggleGroup(timeControlButtons);
        ToggleButton oneMinPlusNineButton = new ToggleButton("1min+9");
        oneMinPlusNineButton.setOnAction(evt -> wordGameController(oneMinPlusNineButton, letterCount, MINUTES, 9, "1min+9"));
        oneMinPlusNineButton.setToggleGroup(timeControlButtons);

        Label rapidLabel = new Label("Rapid:");
        ToggleButton threeMinButton = new ToggleButton("3min");
        threeMinButton.setOnAction(evt -> wordGameController(threeMinButton, letterCount, 3 * MINUTES, 0, "3min"));
        threeMinButton.setToggleGroup(timeControlButtons);
        ToggleButton threeMinPlusThreeButton = new ToggleButton("3min+3");
        threeMinPlusThreeButton.setOnAction(evt -> wordGameController(threeMinPlusThreeButton, letterCount, 3 * MINUTES, 3, "3min+3"));
        threeMinPlusThreeButton.setToggleGroup(timeControlButtons);
        ToggleButton threeMinPlusNineButton = new ToggleButton("3min+9");
        threeMinPlusNineButton.setOnAction(evt -> wordGameController(threeMinPlusNineButton, letterCount, 3 * MINUTES, 9, "3min+9"));
        threeMinPlusNineButton.setToggleGroup(timeControlButtons);
        ToggleButton threeMinPlusEighteenButton = new ToggleButton("3min+18");
        threeMinPlusEighteenButton.setOnAction(evt -> wordGameController(threeMinPlusEighteenButton, letterCount, 3 * MINUTES, 18, "3min+18"));
        threeMinPlusEighteenButton.setToggleGroup(timeControlButtons);

        Label classicLabel = new Label("Classical:");
        ToggleButton nineMinButton = new ToggleButton("9min");
        nineMinButton.setOnAction(evt -> wordGameController(nineMinButton, letterCount, 9 * MINUTES, 0, "9min"));
        nineMinButton.setToggleGroup(timeControlButtons);
        ToggleButton nineMinPlusThreeButton = new ToggleButton("9min+3");
        nineMinPlusThreeButton.setOnAction(evt -> wordGameController(nineMinPlusThreeButton, letterCount, 9 * MINUTES, 3, "9min+3"));
        nineMinPlusThreeButton.setToggleGroup(timeControlButtons);
        ToggleButton nineMinPlusNineButton = new ToggleButton("9min+9");
        nineMinPlusNineButton.setOnAction(evt -> wordGameController(nineMinPlusNineButton, letterCount, 9 * MINUTES, 9, "9min+9"));
        nineMinPlusNineButton.setToggleGroup(timeControlButtons);
        ToggleButton nineMinPlusEighteenButton = new ToggleButton("9min+18");
        nineMinPlusEighteenButton.setOnAction(evt -> wordGameController(nineMinPlusEighteenButton, letterCount, 9 * MINUTES, 18, "9min+18"));
        nineMinPlusEighteenButton.setToggleGroup(timeControlButtons);

        Label extendedLabel = new Label("Extended:");
        ToggleButton fifteenMinButton = new ToggleButton("15min");
        fifteenMinButton.setOnAction(evt -> wordGameController(fifteenMinButton, letterCount, 15 * MINUTES, 0, "15min"));
        fifteenMinButton.setToggleGroup(timeControlButtons);
        ToggleButton thirtyMinButton = new ToggleButton("30min");
        thirtyMinButton.setOnAction(evt -> wordGameController(thirtyMinButton, letterCount, 30 * MINUTES, 0, "30min"));
        thirtyMinButton.setToggleGroup(timeControlButtons);
        ToggleButton oneHourButton = new ToggleButton("1hr");
        oneHourButton.setOnAction(evt -> wordGameController(oneHourButton, letterCount, 1 * HOURS, 0, "1hr"));
        oneHourButton.setToggleGroup(timeControlButtons);
        ToggleButton twoHourButton = new ToggleButton("2hr");
        twoHourButton.setOnAction(evt -> wordGameController(twoHourButton, letterCount, 2 * HOURS, 0, "2hr"));
        twoHourButton.setToggleGroup(timeControlButtons);

        if (gameType.equals("ANAGRAMS")) {
            threeMinButton.fire();
        } else if (gameType.equals("WORDLE")) {
            casualButton.fire();

            twentySecPlusOneButton.setDisable(true);
            twentySecPlusTwoButton.setDisable(true);
            twentySecPlusFourButton.setDisable(true);

            oneMinPlusThreeButton.setDisable(true);
            oneMinPlusSixButton.setDisable(true);
            oneMinPlusNineButton.setDisable(true);

            threeMinPlusThreeButton.setDisable(true);
            threeMinPlusNineButton.setDisable(true);
            threeMinPlusEighteenButton.setDisable(true);

            nineMinPlusThreeButton.setDisable(true);
            nineMinPlusNineButton.setDisable(true);
            nineMinPlusEighteenButton.setDisable(true);
        } else if (gameType.equals("PANGRAMS")) {
            casualButton.fire();
        }

        showHintsBox = new CheckBox("Show hints");
        showHintsBox.setOnAction(evt -> showHints = showHintsBox.isSelected());

        Button startButton = new Button("Start Game");
        startButton.setOnAction(evt -> startGame(primaryStage));
        
        Button backButton = new Button("BACK");
        backButton.setOnAction(evt -> primaryStage.setScene(previousScene));

        VBox screen = new VBox(5);

        HBox casualButtonCollumn = new HBox();
        casualButtonCollumn.getChildren().addAll(casualButton);
        HBox twentySecButtonCollumns = new HBox();
        twentySecButtonCollumns.getChildren().addAll(twentySecButton, twentySecPlusOneButton, twentySecPlusTwoButton, twentySecPlusFourButton);
        HBox oneMinButtonCollumns = new HBox();
        oneMinButtonCollumns.getChildren().addAll(oneMinButton, oneMinPlusThreeButton, oneMinPlusSixButton, oneMinPlusNineButton);
        HBox threeMinButtonCollumns = new HBox();
        threeMinButtonCollumns.getChildren().addAll(threeMinButton, threeMinPlusThreeButton, threeMinPlusNineButton, threeMinPlusEighteenButton);
        HBox nineMinButtonCollumns = new HBox();
        nineMinButtonCollumns.getChildren().addAll(nineMinButton, nineMinPlusThreeButton, nineMinPlusNineButton, nineMinPlusEighteenButton);
        HBox longButtonCollumns = new HBox();
        longButtonCollumns.getChildren().addAll(fifteenMinButton, thirtyMinButton, oneHourButton, twoHourButton);

        screen.getChildren().addAll(promptLabel, letterLabel, letterCountBox, timeControlLabel, casualLabel, casualButtonCollumn, bulletLabel, twentySecButtonCollumns, blitzLabel, oneMinButtonCollumns, rapidLabel, threeMinButtonCollumns, classicLabel, nineMinButtonCollumns, extendedLabel, longButtonCollumns, showHintsBox, startButton, backButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        wordOptionsScene = new Scene(layout);
        primaryStage.setScene(wordOptionsScene);
    }
}

abstract class GameInterface {
    static Scene finishedScene;

    protected void showCompletionScreen() {
        //
    }
}

final class AnagramsInterface extends GameInterface {
    private static Anagrams anagrams;

    static Scene anagramScene;

    static List<String> guess;
    static int letterTotal;

    static int points;
    static int time;

    static void buttonPress(Button letterButton) {
        letterButton.setDisable(true);
        guess.add(Character.toString(letterButton.getId().charAt(0)));

        for (int i = 0; i < guess.size(); i++) {
            Label letterLabel = (Label) anagramScene.lookup("#" + Integer.toString(i));
            letterLabel.setText(guess.get(i));
        }
    }

    static void guess() {
        String guessStr = String.join("", guess);
        String output = anagrams.turn(guessStr);
        if (output.equals(guessStr + "! AMAZING!")) {
            for (int i = 0; i < guess.size(); i++) {
                String letter = Character.toString(anagrams.answer.charAt(i));
                Button letterButton = (Button) anagramScene.lookup("#" + letter + Integer.toString(i));
                letterButton.setStyle("-fx-base: lightgreen");
            }
        }

        Label statusLabel = (Label) anagramScene.lookup("#status");
        statusLabel.setText(output);

        Label pointsLabel = (Label) anagramScene.lookup("#points");
        pointsLabel.setText(Integer.toString(anagrams.points));

        clear();
    }

    static void delete() {
        if (guess.size() == 0) {
            return;
        }

        int lastIndex = guess.size() - 1;
        String letter = guess.remove(lastIndex);

        Label letterLabel = (Label) anagramScene.lookup("#" + Integer.toString(lastIndex));
        letterLabel.setText("");

        for (int i = 0; i < letterTotal; i++) {
            Button letterButton = (Button) anagramScene.lookup("#" + letter + Integer.toString(i));
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
            Label letterLabel = (Label) anagramScene.lookup("#" + Integer.toString(i));
            letterLabel.setText("");

            String letter = Character.toString(oldString.charAt(i));
            Button letterButton = (Button) anagramScene.lookup("#" + letter + Integer.toString(i));
            letterButton.setText(Character.toString(anagrams.answer.charAt(i)));
            letterButton.setId(Character.toString(anagrams.answer.charAt(i)) + Integer.toString(i));
            letterButton.setDisable(false);
        }
    }

    static void clear() {
        guess = new ArrayList<String>(letterTotal);

        for (int i = 0; i < letterTotal; i++) {
            Label letterLabel = (Label) anagramScene.lookup("#" + Integer.toString(i));
            letterLabel.setText("");

            String letter = Character.toString(anagrams.answer.charAt(i));
            Button letterButton = (Button) anagramScene.lookup("#" + letter + Integer.toString(i));
            letterButton.setDisable(false);
        }
    }

    public static void back(Stage primaryStage, Scene previousScene) {
        anagrams.timer.cancel();
        primaryStage.setScene(previousScene);
    }

    public static void start(Stage primaryStage, Scene previousScene, int letterCount, int totalTimeSet, int addTimeSet, boolean showHints) {
        anagrams = new Anagrams(letterCount, showHints, anagramScene);

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
            letterButton.setOnAction(evt -> buttonPress(letterButton));
            
            buttonBox.getChildren().add(letterButton);
        }

        HBox gameButtons = new HBox(15);

        Button enterButton = new Button("Enter");
        enterButton.setId("enter");
        enterButton.setFocusTraversable(false);
        enterButton.setOnAction(evt -> guess());

        Button deleteButton = new Button("Delete");
        deleteButton.setId("delete");
        deleteButton.setFocusTraversable(false);
        deleteButton.setOnAction(evt -> delete());

        Button shuffleButton = new Button("Shuffle");
        shuffleButton.setId("shuffle");
        shuffleButton.setFocusTraversable(false);
        shuffleButton.setOnAction(evt -> shuffle());

        Button clearButton = new Button("Clear");
        clearButton.setId("clear");
        clearButton.setFocusTraversable(false);
        clearButton.setOnAction(evt -> clear());

        gameButtons.getChildren().addAll(shuffleButton, enterButton, deleteButton, clearButton);

        Label statusLabel = new Label("Type or click to play a word!");
        statusLabel.setId("status");

        Button backButton = new Button("Back");
        backButton.setFocusTraversable(false);
        backButton.setOnAction(evt -> back(primaryStage, previousScene));

        VBox screen = new VBox(5);
        screen.getChildren().addAll(promptLabel, infoBox, labelBox, buttonBox, gameButtons, statusLabel, backButton);

        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        anagramScene = new Scene(layout);
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
            }
        });

        if (totalTimeSet != 0) {
            anagrams.setTimer(totalTimeSet, addTimeSet, anagramScene);
        }

        primaryStage.setScene(anagramScene);
        primaryStage.show();
    }
}

final class WordleInterface extends GameInterface {
    private static Wordle wordle;

    public static void start(Stage primaryStage, Scene previousScene, int letterCount, int totalTimeSet, int addTimeSet, boolean showHints) {
        //
    }
}

final class PangramsInterface extends GameInterface {
    private static Pangrams pangrams;

    public static void start(Stage primaryStage, Scene previousScene, int letterCount, int totalTimeSet, int addTimeSet, boolean showHints) {
        //
    }
}
