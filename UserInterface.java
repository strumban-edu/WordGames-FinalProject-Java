import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import wordgames.games.*;

public class UserInterface extends Application {
    static Label turnLabel;
    static Button[][] buttonArray;
    static Button startButton;
    static Scene sceneVar;

    Anagrams anagrams;
    Wordle wordle;
    SpellingBee spellingBee;
    
    public UserInterface() {
        buttonArray = new Button[5][5];
        for (int i = 0; i < 5; i++) {
            Button[] buttonRow = new Button[5];
            for (int j = 0; j < 5; j++) {
                buttonRow[j] = new Button();
                Button buttonVar = buttonRow[j];
                buttonVar.setMinSize(60, 60);
                buttonVar.setId(Integer.toString(10 * i + j));
                buttonVar.setOnAction(evt -> buttonClick(buttonVar.getId()));
            }
            buttonArray[i] = buttonRow;
        }
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

    private void buttonClick(String id) {
        Button button = (Button) sceneVar.lookup("#" + id);
        int convertedID = Integer.parseInt(id);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        turnLabel = new Label();

        VBox screen = new VBox(5);

        HBox buttonColumns = new HBox();
        for (int i = 0; i < 5; i++) {
            VBox buttonRows = new VBox();
            for (Button btn : buttonArray[i]) {
                buttonRows.getChildren().add(btn);
            }
            buttonColumns.getChildren().add(buttonRows);
        }

        screen.getChildren().addAll(buttonColumns, turnLabel);
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
