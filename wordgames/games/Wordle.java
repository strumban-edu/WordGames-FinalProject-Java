package wordgames.games;

import java.util.List;

public final class Wordle extends wordgames.WordGameGeneric {
    int[] clues;

    public Wordle(int letters) {
        List<String> wordOptions = words.get(letters - 3);
        this.answer = wordOptions.get(random.nextInt(wordOptions.size()));
        this.clues = new int[letters];
    }

    public final boolean turn(String guess) {
        if (guess.equals(this.answer)) {
            for (int i = 0; i < this.answer.length(); i++) {
                this.clues[i] = 2;
            }
            this.finished = true;

            return true;
        } else if (guess.length() == this.answer.length()) {
            String tempStr = this.answer;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < this.answer.length(); j++) {
                    String guessLetter = String.valueOf(guess.charAt(j));
                    String answerLetter = String.valueOf(this.answer.charAt(j));

                    if (i == 0 && guessLetter.equals(answerLetter)) {
                        this.clues[j] = 2;
                    } else if (i == 1 && tempStr.contains(guessLetter)) {
                        this.clues[j] = 1;
                    }

                    tempStr.replaceFirst(guessLetter, "");
                }
            }

            return true;
        }
        
        return false;
    }
}
