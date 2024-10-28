package App.Entities;

import App.Events.Event;
import App.Events.EventPassengerArrivedToStopFromTram;
import App.Events.EventTramArrivedToStop;

public class Tram extends Vehicle {
//    private Passenger[] passengers;
    private int passangerCount;
//    private Passenger[][] stopRequest; // Here I put information about users who want to get off at the stop

    private Passenger[][] stopRequest;
    private int[] stopRequestCount;
//    TramPassengerHeapPriorityQueue tramPassengerHeapPriorityQueue;
    private TramLine route;
    private int direction;
    private int currentStop;
    private StopDistance[] stopDistances;
    private int stopDistanceCount;
    private int defaultLocalTime = 360;
    private int localTime = defaultLocalTime;


    public Tram(int capacity, int sideNumber, int direction,  TramLine currentRoute) {
        super(capacity, sideNumber);
        this.direction = direction;
        route = currentRoute;
        currentStop = direction == -1 ? route.getLength()-1 : -1;
//        passengers = new Passenger[capacity];
        stopRequest = new Passenger[route.getLength()][capacity];
        stopRequestCount = new int[route.getLength()];
//        tramPassengerHeapPriorityQueue = new TramPassengerHeapPriorityQueue();
        stopDistances = new StopDistance[route.getLength()*2];
//        this.sideNumber = sideNumber;

    }
    public void moveToNextStop() {
        currentStop += 1;
        localTime += getTimeToNextStopByDistance(0);
    }
    public Event doMoveToNextStop(int dayNum) {
        // Moves to the next stop along its route
        currentStop += 1;
        localTime += getTimeToNextStopByDistance(0);
        Stop stop = getStopByDistance(0);
        Event event = new EventTramArrivedToStop(dayNum, localTime, this, stop);
        return event;
    }

    public Event doTakePassengers(int day, int time, Stop stop) {
        return new EventPassengerArrivedToStopFromTram(day, time, this, stop);
    }

    public int getNextStopNumByDistance(int distance) {
        int directionLok = checkDirection(distance);
        int distanceIndex = currentStop + distance;
        int nextNumIndex = distanceIndex % route.getLength();
        int nextNum = directionLok == 1 ? nextNumIndex : route.getLength()-1 - nextNumIndex;
        return nextNum;
    }
    public int getHowMuchStopsLeft() {
        // Returns the number of stops to the next stop
        int howMuchPassed = currentStop % route.getLength();
        int left = route.getLength()-1 - howMuchPassed;

        return left;
    }
    public int getHowMuchWantToLeave() {
        int wantToLeave = stopRequestCount[getNextStopNumByDistance(0)];
        return wantToLeave;
    }

    public boolean isTramHome() {
        boolean isInTheEnd = getHowMuchStopsLeft() == 0;
        boolean isItMyFirst = direction == -1 ? getNextStopNumByDistance(0) == route.getLength()-1 : getNextStopNumByDistance(0) == 0;
        return isInTheEnd && isItMyFirst;
    }

    ////!! Adding a passenger to the tram !!////




    public boolean boardPassenger(Passenger passenger, int whichStop) {
        // Adds a passenger to the tram
        if (isTramFull()) {
            return false;
        }
        passangerCount++;
        int stopId = getNextStopNumByDistance(whichStop);
        stopRequest[stopId][stopRequestCount[stopId]++] = passenger;
//        System.out.println("Pasazer "+ passenger.getId()+", Which stop selected: " + stopId + ", stopReqCount: "+ stopRequestCount[whichStop] +" Global passenger count: "+passangerCount + " Przystanek "+ getNextStopNumByDistance(0) + ", name " + getStopNameByDistance(0));

        return true;
    }


    ////!! END OF Adding a passenger to the tram !!////

    ////!! Removing a passenger from the tram !!////

    public Passenger tryUnboardPassenger() {
        // Adds a passenger to the tram
//        System.out.println("Passenger count: "+passangerCount + " Przystanek "+ getNextStopNumByDistance(0));
        int przystanekIdx = getNextStopNumByDistance(0);
        if (stopRequestCount[przystanekIdx] <= 0 ){
            return null;
        }
        Passenger passenger = stopRequest[przystanekIdx][--stopRequestCount[przystanekIdx]];
        passangerCount--;
        return passenger;
    }


    private int checkDirection(int distance){
        int distanceIndex = currentStop + distance;
        if (route.getLength() <= 0) {
            return 0;
        }
        // 1 - forward, 1 - backward
        int whichDirection;
        if (direction == 1) {
            whichDirection = (distanceIndex / route.getLength()) % 2 == 0 ? 1 : -1;
        } else {
            whichDirection = (distanceIndex / route.getLength()) % 2 == 0 ? -1 : 1;
        }
        return whichDirection * direction;
    }

    public boolean isTramFull() {
        // Returns true if the tram is full
        if (passangerCount >= getCapacity()){
            return true;
        }
        return false;
    }

    public void resetLocalTime(){
        localTime = defaultLocalTime;
    }

    public void resetStopCounter(){
        currentStop = direction == -1 ? route.getLength()-1 : -1;
    }



    public void setCurrentStop(int stop) {
        currentStop = stop;
    }
    public void setLocalTime(int time) {
        localTime = time;
    }

    public void setDefaultLocalTime(int time) {
        defaultLocalTime = time;
    }


    public int getLocalTime() {
        return localTime;
    }

    public int getTramLineName(){
        return route.getLineName();
    }

    public TramLine getTramLine(){
        return route;
    }

    public String getStopNameByDistance(int distance) {
        String stopName = getStopByDistance(distance) != null ? getStopByDistance(distance).getName() : null;
        return stopName;
    }

    public Stop getStopByDistance(int distance) {
//        if (currentStop+distance >= route.getLength() || currentStop+distance < 0) {
//            return null;
//        }
        return route.getStops()[getNextStopNumByDistance(distance)].getStop();
    }

    public int getTimeToNextStop() {
        return getTimeToNextStopByDistance(1);
    }

    public int getTimeToNextStopByDistance(int i) {
        int timeToStop = (checkDirection(i) == 1) ? route.getStops()[getNextStopNumByDistance(i)].getTravelTimeTo()
                : route.getStops()[getNextStopNumByDistance(i)].getTravelTimeFrom();
        return timeToStop;
    }

    public void addStopDistance(StopDistance stopDistance){
        stopDistances[stopDistanceCount++] = stopDistance;
    }

    public StopDistance[] getStopDistances() {
        return stopDistances;
    }

    public int getHowMuchSeatsLeft() {
        return getCapacity()-passangerCount;
    }

    public int getRouteLength() {
        return route.getLength();
    }

    public int getDiraction() {
        return direction;
    }


}


// Co to jest programowanie, to trzeba najpierw zrozumiec, wymyslic i stworzyc
// Wazne jest czytanie ze zrozumieniem. Glownie szukanie metody