package userinterfaces.playable.wordgames;

import java.util.List;

import wordgames.games.*;

public abstract class WordGameUserInterface extends wordgames.WordGameOptions {
    protected static List<String> guess;
    protected static int letterTotal;

    protected void endGame(int points, int totalTime) {
        //
    }

    private void showEndScreen(Anagrams anagrams) {
        //
    }

    private void showEndScreen(Pangrams pangrams) {
        //
    }

    private void showEndScreen(Wordle wordle) {
        //
    }
}
