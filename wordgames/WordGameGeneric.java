package wordgames;

import userinterfaces.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public abstract class WordGameGeneric {
    static private Scene currentScene;

    static protected Random random;    
    static protected List<List<String>> words;
    static protected List<List<String>> wordsUnique;

    protected List<Set<String>> wordOptions;
    protected List<String> playedWords;

    public Timer timer;
    public int timeSet;
    protected int timeAdd;

    public String gameType;
    public String answer;
    public int points;
    public boolean finished;

    public WordGameGeneric() {
        random = new Random();
        words = new ArrayList<List<String>>(13);
        wordsUnique = new ArrayList<List<String>>(26);

        this.wordOptions = new ArrayList<Set<String>>(13);
        this.playedWords = new ArrayList<>();

        //Set points to 0, set game state to running
        this.points = 0;
        this.finished = false;

        for (int i = 0; i < 13; i++) {
            words.add(new ArrayList<String>());
            this.wordOptions.add(new HashSet<String>());
        }

        for (int i = 0; i < 26; i++) {
            wordsUnique.add(new ArrayList<String>());
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
                wordsUnique.get(uniqueChars.size() - 1).add(word);
            }
            wordFileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Word file could not be found or read.");
        }
    }

    protected void getCombos(String str, String sub) {
        int stringLen = str.length();
        int subLen = sub.length();

        if (subLen >= 3 && words.get(subLen - 3).contains(sub)) {
            wordOptions.get(subLen - 3).add(sub);
        }
        
        if (stringLen > 0) {
            for (int i = 0; i < stringLen; i++) {
                getCombos(str.substring(0, i) + str.substring(i + 1, stringLen), sub + str.charAt(i));
            }
        }
    }

    private static String formatTime(int milliTime) {
        return String.format("%02d:%02d", 
            TimeUnit.MILLISECONDS.toMinutes(milliTime),
            TimeUnit.MILLISECONDS.toSeconds(milliTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliTime))
        );
    }

    private void setInterval() {
        if (this.timeSet == 1) {
            this.finished = true;
            this.timer.cancel();
        }
        this.timeSet -= 1000;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label timeLabel = (Label) currentScene.lookup("#time");
                timeLabel.setText(formatTime(timeSet));
            }
        });
    }
    
    protected void addInterval(int timeAdd) {
        this.timeSet += timeAdd;
    }
 
    public void setTimer(int timeMode, int addTime, Scene currScene) {
        this.timer = new Timer();
        this.timeSet = timeMode;
        this.timeAdd = addTime;

        currentScene = currScene;
        Label timeLabel = (Label) currentScene.lookup("#time");
        timeLabel.setText(formatTime(this.timeSet));

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                setInterval();
            }
        }, 0, 1000l);
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
