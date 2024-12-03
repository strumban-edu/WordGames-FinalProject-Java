package wordgames.games;

import java.util.List;

public final class Wordle extends wordgames.WordGameGeneric {
    public List<String> wordList;

    public int[] clues;
    public int guessNum;
    public boolean gameWon;

    public Wordle() {
        wordList = words.get(letterCount - 3);
        this.answer = wordList.get(random.nextInt(wordList.size()));
        
        this.clues = new int[letterCount];
        this.guessNum = 0;

        this.gameWon = false;
    }

    public final String turn(String guess) {
        if (this.finished) {
            return "Game Complete";
        }

        this.clues = new int[letterCount];

        if (guess.equals(this.answer)) {
            for (int i = 0; i < this.answer.length(); i++) {
                this.clues[i] = 2;
            }

            this.finished = true;
            this.gameWon = true;
            return "You Won!";
        } else if (guess.length() == this.answer.length()) {
            String tempStr = this.answer;

            for (int pass = 0; pass < 2; pass++) {
                for (int i = 0; i < this.answer.length(); i++) {
                    String guessLetter = String.valueOf(guess.charAt(i));
                    String answerLetter = String.valueOf(this.answer.charAt(i));

                    if (pass == 0) {
                        if (guessLetter.equals(answerLetter)) {
                            if (showHints) {
                                this.clues[i] = 2;
                            } else {
                                this.clues[i] = 1;
                            }
                            
                            tempStr.replaceFirst(guessLetter, "");
                        }
                        continue;
                    }

                    if (tempStr.contains(guessLetter) && this.clues[i] == 0) {
                        this.clues[i] = 1;
                        tempStr.replaceFirst(guessLetter, "");
                    }
                }
            }
        }
        
        if (guessNum > this.answer.length()) {
            this.finished = true;
            return "Game Over!";
        }
        
        return "Try again.";
    }
}
