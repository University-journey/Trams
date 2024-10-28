package App.Events;

import App.Entities.Passenger;

public class EventPassengerArrivedHome extends Event {
    private Passenger passenger;
    private boolean status;

    public EventPassengerArrivedHome(int day, int time, Passenger passenger) {
        super(day, time);
        this.passenger = passenger;
    }

    @Override
    public void runAndPrint() {
        print(toString());
    }
    public String toString() {
        return "Pasazer " + passenger.getId() + " poszedl do domu";
    }

    @Override
    public EventType getType() {
        return EventType.PASSENGER_HOME_ARRIVAL;
    }
}
