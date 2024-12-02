package userinterfaces;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.*;

public class UserInterface extends Application {
    public static Stage stage;
    public static Scene homeScene;
    public static Scene stratGameScene;
    public static Scene wordGameScene;

    public static Scene currentScene;
    public static Scene previousScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        Label promptLabel = new Label("Select a game type:");

        Button strategyGamesButton = new Button("STRATEGY GAMES");
        strategyGamesButton.setMinSize(60, 60);
        strategyGamesButton.setOnAction(event -> new SelectStrategyGame());

        Button wordGamesButton = new Button("WORD GAMES");
        wordGamesButton.setMinSize(60, 60);
        wordGamesButton.setOnAction(event -> new SelectWordGame());

        Button quitButton = new Button("QUIT");
        quitButton.setMinSize(60, 60);
        quitButton.setOnAction(event -> Platform.exit());

        VBox screen = new VBox(5);

        HBox gameBox = new HBox();
        gameBox.getChildren().addAll(strategyGamesButton, wordGamesButton);

        screen.getChildren().addAll(promptLabel, gameBox, quitButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        homeScene = new Scene(layout);
        currentScene = homeScene;
        stage.setScene(currentScene);

        stage.show();
    }
}
