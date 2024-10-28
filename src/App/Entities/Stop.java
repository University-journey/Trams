package App.Entities;

import App.Events.Event;
import App.Events.EventPassengerBoardsTram;
import App.Events.EventStopAskPassengersToLeave;

public class Stop {
    private String name;
    private int capacity;
    private Passenger[] passengers;
    private int passengersCount;


    public Stop(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        passengers = new Passenger[capacity];
        passengersCount = 0;
//        schedule = new ScheduleQueue();

    }

    public boolean addPassenger(Passenger passenger) {
        if (passengersCount >= capacity) {
            return false;
        }
//        System.out.println("XXXXXDDDD " + passengersCount + " " + capacity);
        // Adds a passenger to the stop if capacity allows
        passengers[passengersCount] = passenger;
        passengersCount++;
        return true;
    }

    public Passenger removePassenger() {
        // Removes a passenger from the stop
        if (passengersCount == 0) {
            return null;
        }
//        System.out.println("XXXXXDDDD " + passengersCount + " " + capacity);
        Passenger passenger = passengers[--passengersCount];
        return passenger;
    }

    public Event doAskPassengersToLeave(int dayNum, int time) {
        return new EventStopAskPassengersToLeave(dayNum, time, this);
    }

    public Event doTrigerPassengerArrivalToTram(int dayNum, int time, Tram tram) {
        return new EventPassengerBoardsTram(dayNum, time, this, tram);
    }

    public Event doTakePassengers(int dayNum, int time, Tram tram) {
        return new EventPassengerBoardsTram(dayNum, time, this, tram);
    }




    public void addStop(Stop stop) {

    }

    public String getName(){
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public int getPassengersCount() {
        return passengersCount;
    }
}