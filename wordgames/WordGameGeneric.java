package wordgames;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public abstract class WordGameGeneric {
    static public Random random;    
    static public List<List<String>> words;
    static public List<List<String>> wordsUnique;

    public List<String> wordOptions;
    public List<String> playedWords;

    public int timeMode;
    public int timeAdd;
    public Timer timer;

    public String answer;
    public int points;
    public boolean finished;

    public WordGameGeneric() {
        random = new Random();
        words = new ArrayList<List<String>>(13);
        wordsUnique = new ArrayList<List<String>>(26);
        playedWords = new ArrayList<>();

        //Set points to 0, set game state to running
        this.points = 0;
        this.finished = false;

        for (int i = 0; i < 13; i++) {
            words.add(new ArrayList<String>());
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

    public void setTimer(int startTime) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                  finished = true;
            }
        }, startTime);
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
