package App.Events;

import App.Entities.Passenger;
import App.Entities.Stop;
import App.Entities.Tram;
import App.Queue.EventHeapPriorityQueue;
import App.Queue.GlobalEventQueue;

public class EventTramArrivedToStop extends Event{
    Tram tram;
    Stop stop;
    public EventTramArrivedToStop(int day, int time, Tram tram, Stop stop) {
        super(day, time);
        this.tram = tram;
        this.stop = stop;
    }

    @Override
    public void runAndPrint() {
        // Sprawdzam czy ktos czeka na przystanku
        EventHeapPriorityQueue mainQueue = GlobalEventQueue.getInstance().getQueue();
        tram.moveToNextStop();
//         Najpierw wypuszczam pasazerow z tramwaju
        int howMuchPassengersLeave = tram.getHowMuchWantToLeave();
        for (int i = 0; i < howMuchPassengersLeave; i++) {
            Event pasLeaving = tram.doTakePassengers(day, time, stop);

            mainQueue.add(pasLeaving);
        }

        // Potem wpuszczam
        int howMuchPassengers = stop.getPassengersCount();
        // Staram sie wpuscic pasazerow na poklad z tego przystanku
        for (int i = 0; i < howMuchPassengers; i++) {
//            int howMuchSeatsLeft = tram.getHowMuchSeatsLeft();
//            if (howMuchSeatsLeft == 0) break;
            Event stopEvent = stop.doTakePassengers(day, time, tram);
            mainQueue.add(stopEvent);
        }
        print(toString());
    }

    public String toString() {
        return "Tramwaj linii " + tram.getTramLineName() + " (nr boczny: " + tram.getSideNumber() + ") przybyl na przystanek " + stop.getName();
    }

    @Override
    public EventType getType() {
        return EventType.TRAM_STOP_ARRIVAL;
    }
}
