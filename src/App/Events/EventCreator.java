package App.Events;

import App.Entities.Stop;
import App.Entities.Tram;
import App.Entities.TramLine;
import App.Entities.Passenger;
import App.Queue.EventHeapPriorityQueue;
import App.Queue.GlobalEventQueue;

public class EventCreator {
    private EventHeapPriorityQueue mainQueue;
    private int dayNum;
    private int curDay; // current day
    private int routeNum;

    public EventCreator(int dayNumA, int routeNumA) {
        dayNum = dayNumA;
        routeNum = routeNumA;
        mainQueue = GlobalEventQueue.getInstance().getQueue();
        curDay = 0;
    }

    public void predictPassangerArrival(final Passenger[] passengers) {
        // I assume that time is more important than memory in this case
        Event[] passengerList = new Event[passengers.length];
        // Implementation for serving passenger arrival
        // O(n)
        for (int i = 0; i < passengers.length; i++) {
            Passenger passenger = passengers[i];
            // From 6:00 to 12:00 passengers are arriving to the stops

            Event passengerEvent = passenger.doArriveToStop(curDay, passenger.getLocation());
            mainQueue.add(passengerEvent);
//            Event passengerEvent = passenger.doArriveToHome(passenger.getLocation(), curDay);
        }
        // O(nlogn)
//        Arrays.sort(passengerList, (o1, o2) -> Integer.compare(o1.getTime(), o2.getTime()));

//        // O(n)
//        for (PassengerEntry passengerFromList : passengerList) {
//            Passenger passenger = passengerFromList.getPassenger();
//            int time = passengerFromList.getArrivalTime();
//            // Here I create PassengerStopEvent to process the passenger arrival
//
//            mainQueue.push(passengerStopEvent);
//
//        }
    }

    public void predictTramLineSchedule(final TramLine[] routes){
        for (TramLine route : routes) {
            for (int i = 0; i < route.getTramsNum(); i++) {
                Tram tram = route.getTram(i);
                while(true) {
                    Event tramMove = tram.doMoveToNextStop(curDay);
                    mainQueue.add(tramMove);
                    if (tram.getLocalTime() > 1440-60) {
                        if (tram.isTramHome()) {
                            break;
                        }
                    }
                }
                tram.resetLocalTime();
                tram.resetStopCounter();

            }
        }
    }

    public void predictStopCleanup(final Stop[] stops){
        for (Stop stop : stops) {
            Event stopCleanupEvent = stop.doAskPassengersToLeave(curDay, 1440-60);
            mainQueue.add(stopCleanupEvent);
        }
    }

    public void resetForNewDay(final TramLine[] routes) {
        curDay++;
        for (TramLine route : routes) {
            for (int i = 0; i < route.getTramsNum(); i++) {
                Tram tram = route.getTram(i);
                tram.resetLocalTime();
                tram.resetStopCounter();
            }
        }
    }



    public EventHeapPriorityQueue getMainQueue() {
        return mainQueue;
    }



}