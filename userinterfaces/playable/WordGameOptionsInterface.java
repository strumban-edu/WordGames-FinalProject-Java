package userinterfaces.playable;

import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import userinterfaces.playable.wordgames.games.*;

public class WordGameOptionsInterface extends wordgames.WordGameOptions {
    private static void startGame() {
        if (gameName.equals("anagrams")) {
            new AnagramsInterface();
        } else if (gameName.equals("wordle")) {
            new WordleInterface();
        } else if (gameName.equals("pangrams")) {
            new PangramsInterface();
        }
    }

    public WordGameOptionsInterface() {
        Label promptLabel = new Label();

        Label letterLabel = new Label("Choose a number of letters");
        HBox letterCountBox = new HBox();
        
        ToggleGroup letterCountButtons = new ToggleGroup();
        for (int i = 0; i <= 11; i++) {
            int letters = i + 4;
            ToggleButton letterButton = new ToggleButton(Integer.toString(letters));
            letterButton.setToggleGroup(letterCountButtons);
            letterButton.setId(Integer.toString(letters));
            letterButton.setOnAction(event -> setLetters(letters));
            letterCountBox.getChildren().add(letterButton);
        }
        
        ToggleGroup timeControlButtons = new ToggleGroup();

        Label timeControlLabel = new Label("Choose a time control");
        Label casualLabel = new Label("Casual:");
        ToggleButton casualButton = new ToggleButton("Unlimited");
        casualButton.setToggleGroup(timeControlButtons);
        casualButton.setOnAction(event -> setTime(0, 0, "Unlimited"));

        Label bulletLabel = new Label("Bullet:");
        ToggleButton twentySecButton = new ToggleButton("20sec");
        twentySecButton.setOnAction(event -> setTime(20 * SECONDS, 0, "20sec"));
        twentySecButton.setToggleGroup(timeControlButtons);
        ToggleButton twentySecP1Button = new ToggleButton("20sec+1");
        twentySecP1Button.setOnAction(event -> setTime(20 * SECONDS, 1, "20sec+1"));
        twentySecP1Button.setToggleGroup(timeControlButtons);
        ToggleButton twentySecP2Button = new ToggleButton("20sec+2");
        twentySecP2Button.setOnAction(event -> setTime(20 * SECONDS, 2, "20sec+2"));
        twentySecP2Button.setToggleGroup(timeControlButtons);
        ToggleButton twentySecP4Button = new ToggleButton("20sec+4");
        twentySecP4Button.setOnAction(event -> setTime(20 * SECONDS, 4, "20sec+4"));
        twentySecP4Button.setToggleGroup(timeControlButtons);

        Label blitzLabel = new Label("Blitz:");
        ToggleButton oneMinButton = new ToggleButton("1min");
        oneMinButton.setToggleGroup(timeControlButtons);
        oneMinButton.setOnAction(event -> setTime(MINUTES, 0, "1min"));
        ToggleButton oneMinP3Button = new ToggleButton("1min+3");
        oneMinP3Button.setOnAction(event -> setTime(MINUTES, 3, "1min+3"));
        oneMinP3Button.setToggleGroup(timeControlButtons);
        ToggleButton oneMinP6Button = new ToggleButton("1min+6");
        oneMinP6Button.setOnAction(event -> setTime(MINUTES, 6, "1min+6"));
        oneMinP6Button.setToggleGroup(timeControlButtons);
        ToggleButton oneMinP9Button = new ToggleButton("1min+9");
        oneMinP9Button.setOnAction(event -> setTime(MINUTES, 9, "1min+9"));
        oneMinP9Button.setToggleGroup(timeControlButtons);

        Label rapidLabel = new Label("Rapid:");
        ToggleButton threeMinButton = new ToggleButton("3min");
        threeMinButton.setOnAction(event -> setTime(3 * MINUTES, 0, "3min"));
        threeMinButton.setToggleGroup(timeControlButtons);
        ToggleButton threeMinP3Button = new ToggleButton("3min+3");
        threeMinP3Button.setOnAction(event -> setTime(3 * MINUTES, 3, "3min+3"));
        threeMinP3Button.setToggleGroup(timeControlButtons);
        ToggleButton threeMinP9Button = new ToggleButton("3min+9");
        threeMinP9Button.setOnAction(event -> setTime(3 * MINUTES, 9, "3min+9"));
        threeMinP9Button.setToggleGroup(timeControlButtons);
        ToggleButton threeMinP18Button = new ToggleButton("3min+18");
        threeMinP18Button.setOnAction(event -> setTime(3 * MINUTES, 18, "3min+18"));
        threeMinP18Button.setToggleGroup(timeControlButtons);

        Label classicLabel = new Label("Classical:");
        ToggleButton nineMinButton = new ToggleButton("9min");
        nineMinButton.setOnAction(event -> setTime(9 * MINUTES, 0, "9min"));
        nineMinButton.setToggleGroup(timeControlButtons);
        ToggleButton nineMinP3Button = new ToggleButton("9min+3");
        nineMinP3Button.setOnAction(event -> setTime(9 * MINUTES, 3, "9min+3"));
        nineMinP3Button.setToggleGroup(timeControlButtons);
        ToggleButton nineMinP9Button = new ToggleButton("9min+9");
        nineMinP9Button.setOnAction(event -> setTime(9 * MINUTES, 9, "9min+9"));
        nineMinP9Button.setToggleGroup(timeControlButtons);
        ToggleButton nineMinP18Button = new ToggleButton("9min+18");
        nineMinP18Button.setOnAction(event -> setTime(9 * MINUTES, 18, "9min+18"));
        nineMinP18Button.setToggleGroup(timeControlButtons);

        Label extendedLabel = new Label("Extended:");
        ToggleButton fifteenMinButton = new ToggleButton("15min");
        fifteenMinButton.setOnAction(event -> setTime(15 * MINUTES, 0, "15min"));
        fifteenMinButton.setToggleGroup(timeControlButtons);
        ToggleButton thirtyMinButton = new ToggleButton("30min");
        thirtyMinButton.setOnAction(event -> setTime(30 * MINUTES, 0, "30min"));
        thirtyMinButton.setToggleGroup(timeControlButtons);
        ToggleButton oneHourButton = new ToggleButton("1hr");
        oneHourButton.setOnAction(event -> setTime(1 * HOURS, 0, "1hr"));
        oneHourButton.setToggleGroup(timeControlButtons);
        ToggleButton twoHourButton = new ToggleButton("2hr");
        twoHourButton.setOnAction(event -> setTime(2 * HOURS, 0, "2hr"));
        twoHourButton.setToggleGroup(timeControlButtons);

        CheckBox showHintsBox = new CheckBox("Show hints");
        showHintsBox.setOnAction(evt -> showHints = showHintsBox.isSelected());

        Button startButton = new Button("Start Game");
        startButton.setOnAction(evt -> startGame());
        
        Button backButton = new Button("BACK");
        backButton.setOnAction(evt -> stage.setScene(previousScene));

        VBox screen = new VBox(5);

        HBox casualButtonCollumn = new HBox();
        casualButtonCollumn.getChildren().addAll(casualButton);
        HBox twentySecButtonCollumns = new HBox();
        twentySecButtonCollumns.getChildren().addAll(twentySecButton, twentySecP1Button, twentySecP2Button, twentySecP4Button);
        HBox oneMinButtonCollumns = new HBox();
        oneMinButtonCollumns.getChildren().addAll(oneMinButton, oneMinP3Button, oneMinP6Button, oneMinP9Button);
        HBox threeMinButtonCollumns = new HBox();
        threeMinButtonCollumns.getChildren().addAll(threeMinButton, threeMinP3Button, threeMinP9Button, threeMinP18Button);
        HBox nineMinButtonCollumns = new HBox();
        nineMinButtonCollumns.getChildren().addAll(nineMinButton, nineMinP3Button, nineMinP9Button, nineMinP18Button);
        HBox longButtonCollumns = new HBox();
        longButtonCollumns.getChildren().addAll(fifteenMinButton, thirtyMinButton, oneHourButton, twoHourButton);

        screen.getChildren().addAll(promptLabel, letterLabel, letterCountBox, timeControlLabel, casualLabel, casualButtonCollumn, bulletLabel, twentySecButtonCollumns, blitzLabel, oneMinButtonCollumns, rapidLabel, threeMinButtonCollumns, classicLabel, nineMinButtonCollumns, extendedLabel, longButtonCollumns, showHintsBox, startButton, backButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        Scene wordOptionsScene = new Scene(layout);
        previousScene = currentScene;
        currentScene = wordOptionsScene;

        if (gameName.equals("anagrams")) {
            threeMinButton.fire();

            ToggleButton letterButton = (ToggleButton) currentScene.lookup("#6");
            letterButton.fire();
        } else if (gameName.equals("wordle")) {
            casualButton.fire();

            ToggleButton letterButton = (ToggleButton) currentScene.lookup("#5");
            letterButton.fire();

            twentySecP1Button.setDisable(true);
            twentySecP2Button.setDisable(true);
            twentySecP4Button.setDisable(true);

            oneMinP3Button.setDisable(true);
            oneMinP6Button.setDisable(true);
            oneMinP9Button.setDisable(true);

            threeMinP3Button.setDisable(true);
            threeMinP9Button.setDisable(true);
            threeMinP18Button.setDisable(true);

            nineMinP3Button.setDisable(true);
            nineMinP9Button.setDisable(true);
            nineMinP18Button.setDisable(true);
        } else if (gameName.equals("pangrams")) {
            casualButton.fire();
            
            ToggleButton letterButton = (ToggleButton) currentScene.lookup("#7");
            letterButton.fire();
        }

        stage.setScene(currentScene);
    }
}
