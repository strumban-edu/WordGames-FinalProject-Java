package userinterfaces.playable.stratgames;

import java.util.List;
import java.util.Set;

import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import wordgames.WordGameGeneric;
import wordgames.games.*;

public abstract class GenericStratGameInterface extends stratgames.StratGameOptions {
    protected static List<String> guess;
    protected static int letterTotal;

    protected void endGame(WordGameGeneric wordgame) {
        wordgame.finished = true;
        if (wordgame.timer != null) {
            wordgame.timer.cancel();
        }
        
        if (gameName.equals("TicTacToe")) {
            //
        }
    }
}
