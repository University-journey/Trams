package App.Events;

import App.Queue.EventHeapPriorityQueue;
import App.Statistic.PassengerStatisticColector;

public class EventDispatcher {
    private EventHeapPriorityQueue mainQueue;
    private int dayNum;
    private int curDay; // current day
    private int curMin; // current minute
//    PassengerStatisticColector statisticColector;
    public EventDispatcher(int dayNum, EventHeapPriorityQueue mainQueue){
        this.mainQueue = mainQueue;
        this.dayNum = dayNum;
        curDay = 0;
        curMin = 0;
//        this.statisticColector = statisticColector;
    }


    // This method execute the events in the queue
    public void executeEvents() {
        while (!mainQueue.isEmpty()) {
//            Event tramEvent = tramQueue.pop();
            Event arrivalEvent = mainQueue.poll();
            curMin += arrivalEvent.getTime()-curMin;
            arrivalEvent.runAndPrint();
        }
    }

    public void addDay() {
        curDay++;
    }

    public void resetForNewDay() {
        curMin = 360;
        addDay();
    }
}
