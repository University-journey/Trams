package App;

import App.Entities.*;
import App.Events.EventCreator;
import App.Events.EventDispatcher;
import App.Queue.EventHeapPriorityQueue;
import App.Statistic.PassengerStatisticColector;

import java.util.Arrays;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        GenerateScene generateScene = new GenerateScene();
        SceneData sceneData = generateScene.generateScene();
        int days = sceneData.getDays();
        Stop[] stops = sceneData.getStops();
        Passenger[] passengers = sceneData.getPassengers();
        TramLine[] routes = sceneData.getRoutes();
        int routeNum = routes.length;

        // Create event manager
        EventCreator eventCreator = new EventCreator(days, routeNum);
        PassengerStatisticColector passengerStatisticColector = PassengerStatisticColector.getInstance(passengers, days);

        //!!!! EVENT CREATION !!!!//
//        // Creating first events for each passangers who will be waiting at the stop



        eventCreator.predictPassangerArrival(passengers);
        eventCreator.predictTramLineSchedule(routes);
        eventCreator.predictStopCleanup(stops);

        EventHeapPriorityQueue eventQueue = eventCreator.getMainQueue();
        EventDispatcher eventDispatcher = new EventDispatcher(days, eventQueue);
        eventDispatcher.executeEvents();
        for (int i = 0; i < days-1;i++){
            eventCreator.resetForNewDay(routes);
            eventDispatcher.resetForNewDay();
            passengerStatisticColector.addDay();

            eventCreator.predictPassangerArrival(passengers);
            eventCreator.predictTramLineSchedule(routes);
            eventCreator.predictStopCleanup(stops);
            eventDispatcher.executeEvents();
        }



        //--------------------


        // Output read data for verification (optional)
        System.out.println("Dni symulacji: " + days);
        System.out.println("Liczba przystankow: " + stops.length);
        System.out.println("Liczba pasazerow: " + passengers.length);
        System.out.println("Liczba linii tramwajowych: " + routes.length);
        for(int i = 0; i< routeNum; i++){
            TramLine line = routes[i];
            System.out.println("| Linia tramwajowa: "+line.getLineName() +
                    ", liczba tramwajow: " + line.getTramsNum()+
                    ", dlugosc trasy: " + line.getLength()
            );
            System.out.println("|\n->");
            RouteSegment[] segments = line.getSegments();
            for(int j=0; j < segments.length; j++){
                System.out.println("  | Nazwa przystanku: " + segments[j].getName()+", czas dojazdu: "+segments[j].getTravelTimeTo() + ", pojemnosc: " + segments[j].getCapacity());
            }
            System.out.println("");

        }

        System.out.println("STATISTIC:");
        passengerStatisticColector.printStatisticsAll();
        passengerStatisticColector.printStatisticsPerDay();
    }


}