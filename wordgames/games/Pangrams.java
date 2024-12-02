package wordgames.games;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public final class Pangrams extends wordgames.WordGameGeneric {
    public Set<Character> letters;
    public char[] specialLetterArr;

    public Pangrams() {
        List<Set<Character>> wordList = wordsUnique.get(letterCount);
        this.letters = wordList.get(random.nextInt(wordList.size()));

        List<Character> letterList = new ArrayList<Character>(this.letters);
        for (int i = 0; i < 1; i++) {
            char specialLetter = letterList.get(random.nextInt(letterList.size()));
            specialLetterArr[i] = specialLetter;
            letters.remove(specialLetter);
        }

        getUniqueCombos();
        shuffle();

        if (totalTime != 0) {
            startTimer();
        }
    }

    private void getUniqueCombos() {
        for (int i = 0; i < words.size(); i++) {
            for (String word : words.get(i)) {
                for (char c : specialLetterArr) {
                    if (word.indexOf(c) < 0) {
                        continue;
                    }
                }

                String lettersLeft = word;
                for (Character letter : letters) {
                    lettersLeft.replace(letter.toString(), "");
                }

                if (lettersLeft.length() == 0) {
                    wordOptions.get(word.length() - 3).add(word);
                }
            }
        }
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
        
        if (this.playedWords.get(guess.length()).contains(guess)) {
            return "Word '" + guess + "'' already played!";
        }

        if (guess.length() < 3) {
            return "Word must be at least three letters";
        }

        for (char specialLetter : specialLetterArr) {
            if (guess.indexOf(specialLetter) < 0) {
                return "Word must contain highlighted letter (" + specialLetter + ")";
            }
        }

        for (String word : wordOptions.get(guess.length() - 3)) {
            if (guess.equals(word)) {
                this.playedWords.get(guess.length()).add(guess);
                
                HashSet<Character> uniqueChars = new HashSet<Character>();
                for (int i = 0; i < word.length(); i++) {
                    uniqueChars.add(word.charAt(i));
                }

                if (uniqueChars.size() == letterCount) {
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
