package App.Statistic;

import App.Entities.Passenger;

public class PassengerStatistic {
    Passenger passenger;
    int lastArrivalTime;
    int travelTimeAll;
    int dayNum;
    int curDay;
    int[] arrivingRecords;
    public PassengerStatistic(Passenger passenger, int dayNum) {
        this.passenger = passenger;
        arrivingRecords = new int[dayNum];
    }


    public void registerTravel(int day, int time) {
        curDay = PassengerStatisticColector.getInstance().getCurDay();
        lastArrivalTime = time;
        arrivingRecords[day]++;
        travelTimeAll++;
    }

    public void registerArriving(int day, int time) {
        lastArrivalTime = time;
    }

    public int[] getArrivingRecordsPerDay() {
        return arrivingRecords;
    }
    public int getTravelTimeAll() {
        return travelTimeAll;
    }

}
