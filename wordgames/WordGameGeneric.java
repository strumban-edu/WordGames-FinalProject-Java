package wordgames;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Collections;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class WordGameGeneric extends wordgames.WordGameOptions {
    static protected Random random;

    static protected List<List<String>> words;
    static protected List<List<Set<Character>>> wordsUnique;

    public List<SortedSet<String>> wordOptions;
    public List<Set<String>> playedWords;

    public Timer timer;

    public String answer;
    public int points;
    public boolean finished;

    public WordGameGeneric() {
        random = new Random();
        words = new ArrayList<List<String>>(13);
        wordsUnique = new ArrayList<List<Set<Character>>>(26);

        this.wordOptions = new ArrayList<SortedSet<String>>(13);
        this.playedWords = new ArrayList<Set<String>>(13);

        //Set points to 0, set game state to running
        this.points = 0;
        this.finished = false;

        for (int i = 0; i < 13; i++) {
            words.add(new ArrayList<String>());
            this.wordOptions.add(new TreeSet<String>());
            this.playedWords.add(new HashSet<String>());
        }

        for (int i = 0; i < 26; i++) {
            wordsUnique.add(new ArrayList<Set<Character>>());
        }
        
        try {
            File wordFile = new File("words.txt");
            Scanner wordFileReader = new Scanner(wordFile);

            while (wordFileReader.hasNextLine()) {
                String word = wordFileReader.nextLine();
                
                //Code for adding words to list by number of letters
                int wordLength = word.length();
                words.get(wordLength - 3).add(word);

                //Code for adding words to list by number of unique characters
                HashSet<Character> uniqueChars = new HashSet<Character>();
                for (int i = 0; i < word.length(); i++) {
                    uniqueChars.add(word.charAt(i));
                }
                wordsUnique.get(uniqueChars.size() - 1).add(uniqueChars);
            }
            wordFileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Word file could not be found or read.");
        }
    }

    private static String formatTime(int milliTime) {
        return String.format("%02d:%02d", 
            TimeUnit.MILLISECONDS.toMinutes(milliTime),
            TimeUnit.MILLISECONDS.toSeconds(milliTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliTime))
        );
    }

    private void setTime() {
        totalTime -= 1000;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label timeLabel = (Label) currentScene.lookup("#time");
                timeLabel.setText(formatTime(totalTime));

                if (totalTime == 0) {
                    Button endGameButton = (Button) currentScene.lookup("#endgame");
                    endGameButton.fire();
                }
            }
        });
    }
    
    protected void addInterval(int timeAdd) {
        totalTime += timeAdd;
    }
 
    public void startTimer() {
        this.timer = new Timer();

        Label timeLabel = (Label) currentScene.lookup("#time");
        timeLabel.setText(formatTime(totalTime));

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                setTime();
            }
        }, 1000l, 1000l);
    }

    public void shuffle() {
        List<Character> charList = new ArrayList<>(this.answer.length());
        for (char c : this.answer.toCharArray()) {
            charList.add(c);
        }

        Collections.shuffle(charList);

        String shuffledStr = new String();
        for (char c : charList) {
            shuffledStr += c;
        }

        this.answer = shuffledStr;
    }

    public abstract String turn(String guess);
}
