package wordgames.games;

import java.util.HashSet;

public final class Pangrams extends wordgames.WordGameGeneric {
    public String middleLetter;
    private int uniqueLetters;

    public Pangrams(int letterCount) {
        this.wordOptions = wordsUnique.get(letterCount);
        this.answer = wordOptions.get(random.nextInt(wordOptions.size()));
        this.uniqueLetters = letterCount;
    }

    public final void points(int wordLength, int letterCount) {
        if (wordLength == 3) {
            this.points += 100;
        } else {
            this.points += wordLength * 100;
        }

        //Essentially double points for using all letters
        if (letterCount == this.uniqueLetters) {
            this.points += wordLength * 100;
        }
    }

    public final boolean turn(String guess) {
        if (!this.finished && !playedWords.contains(guess) && guess.contains(middleLetter) && guess.length() >= 3) {
            if (guess.equals(this.answer)) {
                this.playedWords.add(guess);
                points(guess.length(), this.uniqueLetters);
                return true;
            }

            for (String word : words.get(guess.length())) {
                if (guess.equals(word)) {
                    this.playedWords.add(guess);
                    
                    HashSet<Character> uniqueChars = new HashSet<Character>();
                    for (int i = 0; i < word.length(); i++) {
                        uniqueChars.add(word.charAt(i));
                    }
                    points(word.length(), uniqueChars.size());
                    
                    return true;
                }
            }
        }

        return false;
    }
}
