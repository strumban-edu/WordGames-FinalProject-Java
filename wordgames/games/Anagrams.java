package wordgames.games;

public final class Anagrams extends wordgames.WordGameGeneric {
    public Anagrams(int wordLength) {
        this.wordOptions = words.get(wordLength - 3);
        this.answer = wordOptions.get(random.nextInt(wordOptions.size()));
    }

    public final void points(int letters) {
        this.points += ((letters - 3) * 400) + 200;
    }

    public final boolean turn(String guess) {
        if (this.finished == false && !playedWords.contains(guess) && guess.length() >= 3) {
            if (guess.equals(this.answer)) {
                this.playedWords.add(guess);
                points(guess.length());
                return true;
            }
            
            for (String word : wordOptions) {
                if (guess.equals(word)) {
                    this.playedWords.add(guess);
                    points(guess.length());
                    return true;
                }
            }
        }

        return false;
    }
}
