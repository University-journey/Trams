package App.Events;

import App.Entities.Passenger;
import App.Entities.Stop;
import App.Entities.Tram;
import App.Losowanie;
import App.Statistic.PassengerStatisticColector;

public class EventPassengerBoardsTram extends Event{
    private Stop stop;
    Passenger passenger;
    private Tram tram;
    private boolean status = false;
    private int selectedStop;

    // To zdarzenie dziala dla kazdego dowolnego pasazera z przystanku
    public EventPassengerBoardsTram(int day, int time, Stop stop, Tram tram) {
        super(day, time);
        this.stop = stop;
        this.tram = tram;
    }

    @Override
    public void runAndPrint() {
        passenger = stop.removePassenger();
        if (passenger == null) return;
//        System.out.println("XDDDDD: "+tram.getSideNumber());
        if (tram.isTramFull()) {
            // Wracam na przystanek
            stop.addPassenger(passenger);
            print(toString());
            return;
        }
//        System.out.println("XDDDDD: "+tram.getSideNumber()+" "+tram.isTramFull());
//        System.out.println(passenger);
        int howMuchStopsLeft = tram.getHowMuchStopsLeft();
        // Wypyisuje informacje o pasazerze, przystanku i ile miejsc
        selectedStop = passenger.chooseStation(howMuchStopsLeft);
//        System.out.println("Passeger" + passenger.getId() +", howMuchStopsLeft: "+howMuchStopsLeft + " selectedStop: "+selectedStop);
        if (selectedStop == 0) {
            // To oznacza, ze nie wpuszczam nikogo, bo dalej nie ma przystankow, tylko zajezdnia
            stop.addPassenger(passenger);
            // Nie wyswietlam nic
            return;
        }
        status = tram.boardPassenger(passenger, selectedStop);
        if (!status) {
            stop.addPassenger(passenger);
            print(toString());
            return;
        }
        print(toString());

        // STATISTIC
        PassengerStatisticColector statisticColector = PassengerStatisticColector.getInstance();
        statisticColector.registerTravel(day, time, passenger);
    }

    public String toString() {
        if (status) {
            return "Pasazer " + passenger.getId() + " na przystanku " + stop.getName() + " wsiadl do tramwaju linii " + tram.getTramLineName() + " (nr boczny: " + tram.getSideNumber() + ") z zamiarem dojechania do przystanku " + tram.getStopNameByDistance(selectedStop);
        } else {
            return "Pasazer " + passenger.getId() + " nie moze wsiasc do tramwaju linii " + tram.getTramLineName() + " (nr boczny: " + tram.getSideNumber() + "), jest pelny.";
        }
    }

    @Override
    public EventType getType() {
        return EventType.PASSANGER_BOARDS_TRAM;
    }

    public Passenger getPassenger() {
        return passenger;
    }
}
