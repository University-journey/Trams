package App.Statistic;

import App.Entities.Passenger;

public class PassengerStatisticColector {
    private static PassengerStatisticColector instance = null;
    private PassengerStatistic[] passengersStatistic;
    private int totalPassengers;
    private int dayNum;
    private int curDay;
    private int[] arrivingRecordsPerDaySum;

    private PassengerStatisticColector(Passenger[] passengers, int dayNum) {
        this.dayNum = dayNum;
        this.passengersStatistic = new PassengerStatistic[passengers.length];
        this.arrivingRecordsPerDaySum = new int[dayNum];
        this.curDay = 0;
        for (Passenger passenger : passengers) {
            this.passengersStatistic[totalPassengers++] = new PassengerStatistic(passenger, dayNum);
        }
    }

    public static PassengerStatisticColector getInstance(Passenger[] passengers, int dayNum) {
        if (instance == null) {
            instance = new PassengerStatisticColector(passengers, dayNum);
        }
        return instance;
    }
    public static PassengerStatisticColector getInstance() {
        return instance;
    }

    public void registerTravel(int day, int time, Passenger passenger) {
        int passengerId = passenger.getId();
        passengersStatistic[passengerId].registerTravel(day, time);
    }

    public void registerArriving(int day, int time, Passenger passenger) {
        int passengerId = passenger.getId();
        passengersStatistic[passengerId].registerArriving(day, time);
    }




    public void printStatisticsAll() {
        int sumTravels = 0;
        int travelTimeAll = 0;
        for (PassengerStatistic passengerStatistic : passengersStatistic) {
            // Laczna liczba przejazdow
            travelTimeAll += passengerStatistic.getTravelTimeAll();
        }

        System.out.println("Laczna liczba przejazdow: " + travelTimeAll);
    }

    public void printStatisticsPerDay() {
        for (PassengerStatistic passengerStatistic : passengersStatistic) {
            int[] arrivingRecordsPerDay = passengerStatistic.getArrivingRecordsPerDay();
            for(int i = 0; i < arrivingRecordsPerDay.length; i++) {
                // Liczba pasazerow przybywajacych w danym dniu
                arrivingRecordsPerDaySum[i] += arrivingRecordsPerDay[i];
            }


        }
        for(int i = 0; i < arrivingRecordsPerDaySum.length; i++) {
            System.out.println("Dzien " + i + " liczba przejazdow: " + arrivingRecordsPerDaySum[i]);
        }
    }

    public void addDay() {
        curDay++;
    }
    public int getCurDay() {
        return curDay;
    }
}