package wordgames;

public class WordGameOptions extends userinterfaces.SelectGameType {
    protected static final int SECONDS = 1000;
    protected static final int MINUTES = SECONDS * 60;
    protected static final int HOURS = MINUTES * 60;

    protected static int letterCount;
    protected static int totalTime;
    protected static int addTime;
    protected static boolean showHints;

    protected static String promptStr;
    protected static int setTimeInt;
    protected static String timeControlStr;

    private static void wordGamePromptController() {
        promptStr = "Playing " + gameName + ", Letters: " + letterCount + ", Time Control: " + timeControlStr;
    }

    protected static void setLetters(int letters) {
        letterCount = letters;

        wordGamePromptController();
    }

    protected static void setTime(int timeSet, int addTimeSet, String timeControl) {
        totalTime = timeSet;
        setTimeInt = timeSet;
        addTime = addTimeSet * SECONDS;
        timeControlStr = timeControl;

        wordGamePromptController();
    }
}
