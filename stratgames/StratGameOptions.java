package stratgames;

public class StratGameOptions extends userinterfaces.SelectGameType {
    protected static final int SECONDS = 1000;
    protected static final int MINUTES = SECONDS * 60;
    protected static final int HOURS = MINUTES * 60;

    protected static int[] totalTime;
    protected static int addTime;

    protected static String promptStr;
    protected static int setTimeInt;
    protected static String timeControlStr;

    private static void stratGamePromptController() {
        promptStr = "Playing " + gameName + ", Time Control: " + timeControlStr;
    }

    protected static void setTime(int timeSet, int addTimeSet, String timeControl) {
        totalTime = timeSet;
        setTimeInt = timeSet;
        addTime = addTimeSet * SECONDS;
        timeControlStr = timeControl;

        stratGamePromptController();
    }
}
