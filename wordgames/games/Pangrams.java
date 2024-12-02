package wordgames.games;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public final class Pangrams extends wordgames.WordGameGeneric {
    public int specialLetterCount;
    public Set<String> letters;
    public Set<String> specialLetterArr;

    public Pangrams() {
        specialLetterCount = 1;

        this.letters = new HashSet<>();
        this.specialLetterArr = new HashSet<>(specialLetterCount);

        List<Set<Character>> wordList = wordsUnique.get(letterCount - 1);
        for (Character c : wordList.get(random.nextInt(wordList.size()))) {
            this.letters.add(c.toString());
        }

        List<String> letterList = new ArrayList<String>(this.letters);
        for (int i = 0; i < specialLetterCount; i++) {
            String specialLetter = letterList.remove(random.nextInt(letterList.size()));

            specialLetterArr.add(specialLetter);
            letters.remove(specialLetter);
        }

        this.answer = String.join("", letters);

        getUniqueCombos();
        shuffle();

        if (totalTime != 0) {
            startTimer();
        }
    }

    private void getUniqueCombos() {
        for (int i = 0; i < words.size(); i++) {
            newWord:
            for (String word : words.get(i)) {
                String lettersLeft = word;
                for (String str : specialLetterArr) {
                    if (word.indexOf(str) < 0) {
                        continue newWord;
                    }

                    lettersLeft = lettersLeft.replace(str, "");
                }

                for (String letter : letters) {
                    lettersLeft = lettersLeft.replace(letter, "");
                }

                if (lettersLeft.length() == 0) {
                    wordOptions.get(i).add(word);
                }
            }
        }
    }

    public final void points(int wordLength) {
        if (wordLength == 3 || wordLength == 4) {
            this.points += 100;
        } else {
            this.points += wordLength * 100;
        }
    }

    public final String turn(String guess) {
        if (this.finished) {
            return "Game Complete";
        }

        if (guess.length() < 3) {
            return "Word must be at least three letters";
        }
        
        if (this.playedWords.get(guess.length() - 3).contains(guess)) {
            return "Word " + guess + " already played!";
        }

        for (String specialLetter : specialLetterArr) {
            if (guess.indexOf(specialLetter) < 0) {
                return "Word must contain highlighted letter (" + specialLetter + ")";
            }
        }

        for (String word : wordOptions.get(guess.length() - 3)) {
            if (guess.equals(word)) {
                this.playedWords.get(guess.length() - 3).add(guess);
                
                HashSet<Character> uniqueChars = new HashSet<Character>();
                
                for (int i = 0; i < word.length(); i++) {
                    uniqueChars.add(word.charAt(i));
                }

                if (uniqueChars.size() == letterCount) {
                    points(word.length() * 2);
                    return guess + "! AMAZING!";
                }
                
                points(word.length());
                return guess + "! Great!";
            }
        }

        return "Word " + guess + " is invalid";
    }
}
