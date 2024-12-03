package wordgames.games;

import java.util.List;

public final class Anagrams extends wordgames.WordGameGeneric {
    public Anagrams() {
        List<String> wordList = words.get(letterCount - 3);
        this.answer = wordList.get(random.nextInt(wordList.size()));
        
        getCombos();
        shuffle();
    }

    private void getCombos() {
        for (int i = 0; i < letterCount - 2; i++) {
            for (String word : words.get(i)) {
                String lettersLeft = word;

                for (Character letter : this.answer.toCharArray()) {
                    lettersLeft = lettersLeft.replaceFirst(letter.toString(), "");
                    
                    if (lettersLeft.length() == 0) {
                        wordOptions.get(i).add(word);
                        break;
                    }
                }
            }
        }
    }

    public final void points(int letters) {
        this.points += ((letters - 3) * 400) + 200;
    }

    public final String turn(String guess) {
        if (this.finished) {
            return "Game Complete";
        }
        
        if (guess.length() < 3) {
            return "Word must be at least three letters.";
        }

        if (this.playedWords.get(guess.length() - 3).contains(guess)) {
            return "Word " + guess + " already played!";
        }

        for (String word : wordOptions.get(guess.length() - 3)) {
            if (guess.equals(word)) {
                this.playedWords.get(guess.length() - 3).add(guess);
                points(guess.length());

                addInterval(addTime);

                if (guess.length() == this.answer.length()) {
                    return guess + "! AMAZING!";
                }

                return guess + "! Great!";
            }
        }

        return "Word " + guess + " is invalid.";
    }
}
