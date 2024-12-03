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
        for (int i = 0; i < wordbuilder.playedWords.size(); i++) {
            int letters = wordbuilder.playedWords.size() + 3 - 1 - i;
            Set<String> guessSet = wordbuilder.playedWords.get(letters - 3);

            if (wordbuilder.wordOptions.get(letters - 3).size() == 0) {
                continue;
            }
            
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
        if (rankPercent == 1) {
            rank = "DICTIONARY DEITY";
        } else if (rankPercent >= 0.95) {
            rank = "Articulation Aristocrat";
        } else if (rankPercent >= 0.8) {
            rank = "Syllogism Sovereign";
        } else if (rankPercent >= 0.7) {
            rank = "Nomenclature Noble";
        } else if (rankPercent >= 0.55) {
            rank = "Vocabulary Viscount";
        } else if (rankPercent >= 0.40) {
            rank = "Composition Count";
        } else if (rankPercent >= 0.25) {
            rank = "Phrase Pharaoh";
        } else if (rankPercent >= 0.15) {
            rank = "Word Wizard";
        } else if (rankPercent >= 0.05) {
            rank = "Reading Royal";
        } else {
            rank = "Lexical Loser";
        }
        Label wordCountLabel = new Label("Guessed " + guessCount + "/" + totalWords + " words");
        Label rankLabel = new Label("Your rank: " + rank);

        HBox optionsBox = new HBox();

        Button doneButton = new Button("Home");
        doneButton.setOnAction(event -> stage.setScene(homeScene));

        Button againButton = new Button("Play Again");
        againButton.setOnAction(event -> stage.setScene(wordGameScene));

        optionsBox.getChildren().addAll(doneButton, againButton);

        VBox screen = new VBox(5);

        screen.getChildren().addAll(pointsLabel, wordCountLabel, rankLabel, results, optionsBox);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        Scene anagramsEndScene = new Scene(layout);
        currentScene = anagramsEndScene;
        stage.setScene(currentScene);
    }
}

class WordleEndScreen extends wordgames.WordGameOptions {
    WordleEndScreen(Wordle wordle) {
        Label winState = new Label();
        if (wordle.gameWon) {
            winState.setText("Yippee! You Won!");
        } else {
            winState.setText("Game Over - You Lost");
        }

        Label word = new Label("The word was: " + wordle.answer);

        Label timeLabel = new Label();
        if (setTimeInt != 0) {
            timeLabel.setText("You had " + wordle.formatTime(totalTime) + " out of " + wordle.formatTime(setTimeInt) + " left.");
        }

        Label turnLabel = new Label("You used " + wordle.guessNum + " out of your " + (wordle.answer.length() + 1) + " turns.");

        HBox optionsBox = new HBox();

        Button doneButton = new Button("Home");
        doneButton.setOnAction(event -> stage.setScene(homeScene));

        Button againButton = new Button("Play Again");
        againButton.setOnAction(event -> stage.setScene(wordGameScene));

        optionsBox.getChildren().addAll(doneButton, againButton);

        VBox screen = new VBox(5);

        screen.getChildren().addAll(winState, word, timeLabel, turnLabel, optionsBox);
        StackPane layout = new StackPane();
        layout.getChildren().add(screen);

        Scene anagramsEndScene = new Scene(layout);
        currentScene = anagramsEndScene;
        stage.setScene(currentScene);
    }
}
