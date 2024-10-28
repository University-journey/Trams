package App.Events;

import static App.printHelper.convertToTime;

public abstract class Event {
    protected int time;
    protected int day;


    public Event(int day, int time) {
        this.day = day;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void print(String description) {
        System.out.println(day+time/1440+ ", " + convertToTime(time) + "; " + description);
    }
    public void printDate() {
        System.out.print(day+ ", " + convertToTime(time) + "; ");
    }
    public abstract String toString();
    public abstract EventType getType();
//     Abstract method to be implemented by subclasses to handle the event's specific actions
    public abstract void runAndPrint();
}
