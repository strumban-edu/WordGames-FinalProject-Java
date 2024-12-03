package stratgames.games;

import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.*;

import stratgames.games.TicTacToe;

public class TicTacToeInterface extends userinterfaces.playable.stratgames.GenericStratGameInterface {
    private static TicTacToe game;
    
    static Label turnLabel;
    static Scene sceneVar;

    private void turn(String id) {
        Button button = (Button) sceneVar.lookup("#" + id);
        int convertedID = Integer.parseInt(id);
        if (!game.win && game.turn(convertedID / 10, convertedID % 10)) {
            if (game.user == 1) {
                button.setText("X");
                turnLabel.setText("PLAYER 1 (X) TURN");
            } else if (game.user == -1) {
                button.setText("O");
                turnLabel.setText("PLAYER 2 (O) TURN");
            }
            
            if (game.win) {
                if (game.user == 1) {
                    turnLabel.setText("PLAYER 1 (X) WON!");
                } else if (game.user == -1) {
                    turnLabel.setText("PLAYER 2 (O) WON!");
                }
            }
        }
    }

    public TicTacToeInterface() {
        int boardSize = 5;
        game = new TicTacToe(boardSize);

        Button[][] buttonArray = new Button[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            Button[] buttonRow = new Button[boardSize];
            for (int j = 0; j < boardSize; j++) {
                buttonRow[j] = new Button();
                Button buttonVar = buttonRow[j];
                buttonVar.setMinSize(60, 60);
                buttonVar.setId(Integer.toString(10 * i + j));
                buttonVar.setOnAction(evt -> turn(buttonVar.getId()));
            }
            buttonArray[i] = buttonRow;
        }
        
        turnLabel = new Label();

        VBox screen = new VBox(5);

        HBox buttonColumns = new HBox();
        for (int i = 0; i < boardSize; i++) {
            VBox buttonRows = new VBox();
            for (Button btn : buttonArray[i]) {
                buttonRows.getChildren().add(btn);
            }
            buttonColumns.getChildren().add(buttonRows);
        }

        screen.getChildren().addAll(buttonColumns, turnLabel);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        sceneVar = new Scene(layout);
        stage.setScene(sceneVar);
    }
}
