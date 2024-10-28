package App.Events;

import App.Entities.Passenger;
import App.Entities.Stop;
import App.Entities.Tram;
import App.Queue.EventHeapPriorityQueue;
import App.Queue.GlobalEventQueue;

public class EventPassengerArrivedToStopFromTram extends Event {
    private Passenger passenger;
    Tram tram;
    private Stop stop;
    private boolean status;

    public EventPassengerArrivedToStopFromTram(int day, int time, Tram tram, Stop stop) {
        super(day, time);
//        this.passenger = passenger;
        this.tram = tram;
        this.stop = stop;
    }

    @Override
    public void runAndPrint() {

        EventHeapPriorityQueue mainQueue = GlobalEventQueue.getInstance().getQueue();
//        status = stop.addPassenger(passenger);
        passenger = tram.tryUnboardPassenger();
        // Jezeli nikt nie chce wysiadac na tym przystanku, to trudno, jade dalej
        if (passenger == null) return;
        // Jesli jest juz po 23:00, to wracam do domu
        int timeToReturnHome = 1440-60;
        if (time >= timeToReturnHome) {
            status = true;
            print(toString());
            Event pasLeaving = passenger.doArriveToHome(day, time);
            mainQueue.add(pasLeaving);
            return;
        }
        // Probuje dodac do przystanku
        status = stop.addPassenger(passenger);
        if (!status) {
            // Jesli przystanek jest pelny,
            // wracam na tramwaj, ale wybieram nowy kierunek
            int stopsLeft = tram.getHowMuchStopsLeft();
            // Jesli nie ma juz przystankow, to wybieram przystanki z powrotem
            if (stopsLeft == 0) {
                stopsLeft = tram.getRouteLength();
            }
            int destination = passenger.chooseStation(stopsLeft);
            tram.boardPassenger(passenger, destination);
        }
//        System.out.println("XDDDDDD " + status);
        print(toString());

    }
    public String toString() {
        if (status) {
            return "Pasazer " + passenger.getId() + " WYSIADL z tramwaju linii " + tram.getTramLineName() + " (nr boczny: " + tram.getSideNumber() + ")"+" na przystanek " + stop.getName() ;
        } else {
            return "Pasazer " + passenger.getId() + " nie moze przybyc na przystanek " + stop.getName() + ", bo jest pelny " + " z tramwaju linii " + tram.getTramLineName() + " (nr boczny: " + tram.getSideNumber() + ")";
        }
    }

    @Override
    public EventType getType() {
        return EventType.PASSANGER_UNBOARDS_TRAM;
    }
}
