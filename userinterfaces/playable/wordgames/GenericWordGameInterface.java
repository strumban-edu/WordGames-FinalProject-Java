package userinterfaces.playable.wordgames;

import java.util.List;
import java.util.Set;

import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import wordgames.WordGameGeneric;
import wordgames.games.*;

public abstract class GenericWordGameInterface extends wordgames.WordGameOptions {
    protected static List<String> guess;
    protected static int letterTotal;

    protected void endGame(WordGameGeneric wordgame) {
        wordgame.finished = true;
        if (wordgame.timer != null) {
            wordgame.timer.cancel();
        }
        
        if (gameName.equals("anagrams")) {
            new WordBuilderEndScreen(wordgame);
        } else if (gameName.equals("pangrams")) {
            new WordBuilderEndScreen(wordgame);
        } else if (gameName.equals("wordle")) {
            new WordleEndScreen((Wordle) wordgame);
        }
    }
}

class WordBuilderEndScreen extends wordgames.WordGameOptions {
    WordBuilderEndScreen(WordGameGeneric wordbuilder) {
        Label pointsLabel = new Label("Total points: " + wordbuilder.points);

        ScrollPane results = new ScrollPane();

        VBox totalWordsBox = new VBox();
        int guessCount = 0;
        int totalWords = 0;
        for (int i = 0; i < letterCount - 2; i++) {
            int letters = letterCount - i;
            Set<String> guessSet = wordbuilder.playedWords.get(letters - 3);

            VBox wordsBox = new VBox();

            Label lengthLabel = new Label(letters + " letter words:");
            
            wordsBox.getChildren().add(lengthLabel);

            for (String word : wordbuilder.wordOptions.get(letters - 3)) {
                totalWords++;
                Label wordLabel = new Label(word);

                if (guessSet.contains(word)) {
                    guessCount++;
                    if (word.length() == letterCount) {
                        wordLabel.setStyle("-fx-text-fill: blue; -fx-font-weight: bold");
                    } else {
                        wordLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold");
                    }
                }

                wordsBox.getChildren().add(wordLabel);
            }

            totalWordsBox.getChildren().add(wordsBox);
        }
        results.setContent(totalWordsBox);
        
        double rankPercent = (double) guessCount / (double) totalWords;
        String rank;
        if (rankPercent == 100) {
            rank = "Word Wizard";
        } else if (rankPercent >= 90) {
            rank = "Word Wizard";
        } else if (rankPercent >= 80) {
            rank = "Word Wizard";
        } else if (rankPercent >= 60) {
            rank = "Phrase Pharaoh";
        } else if (rankPercent >= 40) {
            rank = "Word Wizard";
        } else if (rankPercent >= 20) {
            rank = "Word Wizard";
        } else if (rankPercent >= 10) {
            rank = "Word Wizard";
        } else {
            rank = "Word Wizard";
        }
        Label wordCountLabel = new Label("Guessed " + guessCount + "/" + totalWords + " words");
        Label rankLabel = new Label("Your rank: " + rank);

        Button doneButton = new Button("Done");
        doneButton.setOnAction(event -> stage.setScene(homeScene));

        VBox screen = new VBox(5);

        screen.getChildren().addAll(pointsLabel, wordCountLabel, rankLabel, results, doneButton);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        Scene anagramsEndScene = new Scene(layout);
        currentScene = anagramsEndScene;
        stage.setScene(currentScene);
    }
}

class PangramsEndScreen extends wordgames.WordGameOptions {
    PangramsEndScreen(Pangrams pangrams) {
        //
    }
}

class WordleEndScreen extends wordgames.WordGameOptions {
    WordleEndScreen(Wordle wordle) {
        //
    }
}
