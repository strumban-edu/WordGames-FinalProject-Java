package wordgames.games;

public final class Wordle extends wordgames.WordGameGeneric {
    int[] clues;
    int guessNum;

    public Wordle(int wordLength) {
        this.wordOptions = words.get(wordLength - 3);
        this.answer = wordOptions.get(random.nextInt(wordOptions.size()));
        this.clues = new int[wordLength];
        this.guessNum = 0;
    }

    public final String turn(String guess) {
        if (!this.finished) {
            guessNum += 1;

            if (guess.equals(this.answer)) {
                for (int i = 0; i < this.answer.length(); i++) {
                    this.clues[i] = 2;
                }
                this.finished = true;
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
            }
        }
        
        if (guessNum > this.answer.length()) {
            this.finished = true;
        }
        
        return "";
    }
}
