package App.Statistic;

public class Record {
    private int day;
    private int time;

    Record(int day, int time) {
        this.day = day;
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }
}
