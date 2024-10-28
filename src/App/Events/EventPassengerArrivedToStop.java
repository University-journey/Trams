package App.Events;

import App.Entities.Passenger;
import App.Entities.Stop;
import App.Statistic.PassengerStatisticColector;

public class EventPassengerArrivedToStop extends Event {
    private Passenger passenger;
    private Stop stop;
    private boolean status;

    public EventPassengerArrivedToStop(int day, int time, Passenger passenger, Stop stop) {
        super(day, time);
        this.passenger = passenger;
        this.stop = stop;
    }

    @Override
    public void runAndPrint() {
        status = stop.addPassenger(passenger);
        print(toString());

        // Stistic
        PassengerStatisticColector statisticColector = PassengerStatisticColector.getInstance();
        statisticColector.registerArriving(day, time, passenger);

    }
    public String toString() {
        if (status) {
            return "Pasazer " + passenger.getId() + " przybyl na przystanek " + stop.getName();
        } else {
            return "Pasazer " + passenger.getId() + " nie moze przybyc na przystanek " + stop.getName() + ", jest pelny.";
        }
    }

    @Override
    public EventType getType() {
        return EventType.PASSENGER_STOP_ARRIVAL;
    }
}
