import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.*;

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
        promptLabel = new Label("What game would you like to play?");

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

        primaryStage.setTitle("Word Games");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);

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
        anagramsButton.setId("anagrams");
        anagramsButton.setOnAction(evt -> WordGameOptions.show(primaryStage, wordGameScene, anagramsButton.getId()));

        wordleButton = new Button("WORDLE");
        wordleButton.setMinSize(60, 60);
        wordleButton.setId("wordle");
        wordleButton.setOnAction(evt -> WordGameOptions.show(primaryStage, wordGameScene, wordleButton.getId()));

        pangramsButton = new Button("PANGRAMS");
        pangramsButton.setMinSize(60, 60);
        pangramsButton.setId("pangrams");
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

        primaryStage.setTitle("Word Games");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);

        wordGameScene = new Scene(layout);
        primaryStage.setScene(wordGameScene);
        primaryStage.show();
    }
}

final class WordGameOptions {
    static Scene wordOptionsScene;

    static Label promptLabel;
    static Button backButton;

    static Button casualButton;
    static Button twentySecButton;
    static Button twentySecPlusOneButton;
    static Button twentySecPlusTwoButton;
    static Button twentySecPlusFourButton;
    static Button oneMinButton;
    static Button oneMinPlusThreeButton;
    static Button oneMinPlusSixButton;
    static Button oneMinPlusNineButton;
    static Button threeMinButton;
    static Button threeMinPlusThreeButton;
    static Button threeMinPlusNineButton;
    static Button threeMinPlusEighteenButton;
    static Button nineMinButton;
    static Button nineMinPlusThreeButton;
    static Button nineMinPlusNineButton;
    static Button nineMinPlusEighteenButton;
    static Button fifteenMinButton;
    static Button thirtyMinButton;
    static Button oneHourButton;
    static Button twoHourButton;

    public static void show(Stage primaryStage, Scene previousScene, String id) {
        promptLabel = new Label("Select a game mode:");
        casualButton = new Button("Casual");
        twentySecButton = new Button("20sec");
        twentySecPlusOneButton = new Button("20sec+1");
        twentySecPlusTwoButton = new Button("20sec+2");
        twentySecPlusFourButton = new Button("20sec+4");
        oneMinButton = new Button("1min");
        oneMinPlusThreeButton = new Button("1min+3");
        oneMinPlusSixButton = new Button("1min+6");
        oneMinPlusNineButton = new Button("1min+9");
        threeMinButton = new Button("3min");
        threeMinPlusThreeButton = new Button("3min+3");
        threeMinPlusNineButton = new Button("3min+9");
        threeMinPlusEighteenButton = new Button("3min+18");
        nineMinButton = new Button("9min");
        nineMinPlusThreeButton = new Button("9min+3");
        nineMinPlusNineButton = new Button("9min+9");
        nineMinPlusEighteenButton = new Button("9min+18");
        fifteenMinButton = new Button("15min");
        thirtyMinButton = new Button("30min");
        oneHourButton = new Button("1hr");
        twoHourButton = new Button("2hr");
        
        backButton = new Button("BACK");
        backButton.setOnAction(event -> {
            primaryStage.setScene(previousScene);
        });

        VBox screen = new VBox(5);

        HBox casualButtonCollumn = new HBox();
        casualButtonCollumn.getChildren().addAll(casualButton);
        HBox oneMinButtonCollumns = new HBox();
        oneMinButtonCollumns.getChildren().addAll(oneMinButton, oneMinPlusThreeButton, oneMinPlusSixButton, oneMinPlusNineButton);
        HBox threeMinButtonCollumns = new HBox();
        threeMinButtonCollumns.getChildren().addAll(threeMinButton, threeMinPlusThreeButton, threeMinPlusNineButton, threeMinPlusEighteenButton);
        HBox nineMinButtonCollumns = new HBox();
        nineMinButtonCollumns.getChildren().addAll(nineMinButton, nineMinPlusThreeButton, nineMinPlusNineButton, nineMinPlusEighteenButton);
        HBox longButtonCollumns = new HBox();
        longButtonCollumns.getChildren().addAll(fifteenMinButton, thirtyMinButton, oneHourButton, twoHourButton);

        screen.getChildren().addAll(promptLabel, casualButtonCollumn, oneMinButtonCollumns, threeMinButtonCollumns, nineMinButtonCollumns, longButtonCollumns, backButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        wordOptionsScene = new Scene(layout);
        primaryStage.setScene(wordOptionsScene);
        primaryStage.show();
    }
}

final class AnagramsInterface {
    //
}

final class WordleInterface {
    //
}

final class PangramsInterface {
    //
}
