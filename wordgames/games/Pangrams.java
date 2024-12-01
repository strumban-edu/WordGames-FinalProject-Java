package wordgames.games;

import java.util.HashSet;
import java.util.List;

public final class Pangrams extends wordgames.WordGameGeneric {
    public String middleLetter;
    private int uniqueLetters;

    public Pangrams(int letterCount, int timeMode, int timeAdd, boolean showHints) {
        this.gameType = "pangrams";

        List<String> wordList = wordsUnique.get(letterCount);
        this.answer = wordList.get(random.nextInt(wordList.size()));

        this.uniqueLetters = letterCount;
        shuffle();
    }

    public final void points(int wordLength) {
        if (wordLength == 3) {
            this.points += 100;
        } else {
            this.points += wordLength * 100;
        }
    }

    public final String turn(String guess) {
        if (this.finished) {
            return "game complete";
        }
        
        if (this.playedWords.contains(guess)) {
            return "Word '" + guess + "'' already played!";
        }

        if (guess.length() < 3) {
            return "Word must be at least three letters";
        }

        if (!guess.contains(middleLetter)) {
            return "Word must contain highlighted letter (" + middleLetter + ")";
        } else {
            for (String word : words.get(guess.length() - 3)) {
                if (guess.equals(word)) {
                    this.playedWords.add(guess);
                    
                    HashSet<Character> uniqueChars = new HashSet<Character>();
                    for (int i = 0; i < word.length(); i++) {
                        uniqueChars.add(word.charAt(i));
                    }

                    if (uniqueChars.size() == this.uniqueLetters) {
                        points(word.length() * 2);
                        return guess + " is a valid word! AMAZING!";
                    } else {
                        points(word.length());
                        return guess + " is a valid word! Great!";
                    }
                }
            }

            return "Word '" + guess + "' is invalid";
        }
    }
}
