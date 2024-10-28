package App;

public class printHelper {
    public static String convertToTime(int timeA) {
        int time = timeA % 1440;
        return String.format("%02d:%02d", time / 60, time % 60);
    }
    public static String convertToDay(int time) {
        return String.format("%02d", time/1440);
    }
}
