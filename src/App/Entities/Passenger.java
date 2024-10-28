package App.Entities;
import App.Events.Event;
//import App.Events.PassengerStopEvent;
import App.Events.EventPassengerArrivedHome;
import App.Events.EventPassengerArrivedToStop;
import App.Losowanie;

public class Passenger {
    private Stop location;
    private int id;
    private int destination;

    public Passenger(Stop location, int idA) {
        this.location = location;
        this.id = idA;
    }

    public int chooseStation(int howMuchStopsLeft) {
        if (howMuchStopsLeft < 1) {
            return 0;
        }
        // Implementation for boarding a tram
        destination = Losowanie.losuj(1, howMuchStopsLeft);
        return destination;
    }

    public Event doArriveToStop(int dayNum, Stop stop ){
        int time = Losowanie.losuj(360, 720);
        Event event = new EventPassengerArrivedToStop(dayNum, time, this, stop);
        return event;
    }

    public Event doArriveToHome(int dayNum, int time) {
        Event event = new EventPassengerArrivedHome(dayNum, time, this);
        return event;
    }

    public int getId(){
        return id;
    }

    public Stop getLocation(){
        return location;
    }
}