package wordgames.games;

import java.util.List;

public final class Anagrams extends wordgames.WordGameGeneric {
    List<String> wordOptions;
    int points;

    public Anagrams(int letters) {
        wordOptions = words.get(letters - 3);
        this.answer = wordOptions.get(random.nextInt(wordOptions.size()));
        this.points = 0;
    }

    public final void points(int letters) {
        this.points += ((letters - 3) * 400) + 200;
    }

    public final boolean turn(String guess) {
        if (guess.length() >= 3) {
            if (guess == this.answer) {
                points(guess.length());
                return true;
            }
            
            for (String word : wordOptions) {
                if (guess == word) {
                    points(guess.length());
                    return true;
                }
            }
        }

        return false;
    }
}
