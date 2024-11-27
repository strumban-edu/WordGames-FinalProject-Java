package wordgames;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public abstract class WordGameGeneric {
    static public Random random;    
    static public List<List<String>> words;
    
    public String answer;
    public boolean finished;

    public WordGameGeneric() {
        words = new ArrayList<List<String>>(12);
        this.finished = false;

        for (int i = 0; i <= 12; i++) {
            words.add(new ArrayList<String>());
        }
        
        try {
            File wordFile = new File("words.txt");
            Scanner wordFileReader = new Scanner(wordFile);

            while (wordFileReader.hasNextLine()) {
                String word = wordFileReader.nextLine();
                int wordLength = word.length();
                words.get(wordLength - 3).add(word);
            }
            wordFileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Word file could not be found or read.");
        }
    }

    public abstract boolean turn(String guess);
}
