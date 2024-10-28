package App.Statistic;

public class PassengerWaitingRecord extends Record{
    private int waitingTime;

    PassengerWaitingRecord(int day, int time, int waitingTime) {
        super(day, time);
        this.waitingTime = waitingTime;
    }
}
