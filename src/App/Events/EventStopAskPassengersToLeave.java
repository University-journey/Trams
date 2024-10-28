package App.Events;

import App.Entities.Passenger;
import App.Entities.Stop;
import App.Queue.EventHeapPriorityQueue;
import App.Queue.GlobalEventQueue;

public class EventStopAskPassengersToLeave extends Event{

    private Passenger passenger;
    private Stop stop;
    private boolean status;

    public EventStopAskPassengersToLeave(int day, int time, Stop stop) {
        super(day, time);
        this.stop = stop;
    }

    @Override
    public void runAndPrint() {
        EventHeapPriorityQueue mainQueue = GlobalEventQueue.getInstance().getQueue();

        int howMuchPassengersLeave = stop.getPassengersCount();
        for (int i = 0; i < howMuchPassengersLeave; i++) {
            passenger = stop.removePassenger();
            if (passenger == null) break;
            Event pasLeaving = passenger.doArriveToHome(day, time);
            mainQueue.add(pasLeaving);
        }


    }
    @Override
    public String toString() {
        return null;
    }

    @Override
    public EventType getType() {
        return null;
    }
}
