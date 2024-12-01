package wordgames.games;

import java.util.List;

import javafx.scene.Scene;

public final class Anagrams extends wordgames.WordGameGeneric {
    public Anagrams(int wordLength, boolean showHints, Scene currScene) {
        this.gameType = "anagrams";

        List<String> wordList = words.get(wordLength - 3);
        this.answer = wordList.get(random.nextInt(wordList.size()));
        getCombos(this.answer, "");

        System.out.println(this.answer);
        shuffle();
    }

    public final void points(int letters) {
        this.points += ((letters - 3) * 400) + 200;
    }

    public final String turn(String guess) {
        if (this.finished) {
            return "game complete";
        }
        
        if (this.playedWords.contains(guess)) {
            return "Word " + guess + " already played!";
        }

        if (guess.length() < 3) {
            return "Word must be at least three letters.";
        } else {
            for (String word : words.get(guess.length() - 3)) {
                if (guess.equals(word)) {
                    this.playedWords.add(guess);
                    points(guess.length());

                    addInterval(timeAdd);

                    if (guess.length() == this.answer.length()) {
                        return guess + "! AMAZING!";
                    }

                    return guess + "! Great!";
                }
            }

            return "Word " + guess + " is invalid.";
        }
    }
}
