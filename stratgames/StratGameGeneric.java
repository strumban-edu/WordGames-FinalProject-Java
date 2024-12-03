package stratgames;

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

public abstract class StratGameGeneric extends wordgames.WordGameOptions {
    static protected Random random;

    static protected int[][] board;

    public int player;
    public int[] points;

    public Timer[] timer;
    public boolean finished;

    public StratGameGeneric() {
        random = new Random();

        //Set points to 0, set game state to running
        this.points = new int[2];
        this.finished = false;
    }

    public String formatTime(int milliTime) {
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
 
    public void startTimer(int player) {
        this.timer[player] = new Timer();

        Label timeLabel = (Label) currentScene.lookup("#time");
        
        timeLabel.setText(formatTime(totalTime));

        this.timer[player].scheduleAtFixedRate(new TimerTask() {
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
