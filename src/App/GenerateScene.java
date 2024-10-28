package App;

import App.Entities.*;

import java.util.Arrays;
import java.util.Scanner;

public class GenerateScene {
    public SceneData generateScene() {
        Scanner scanner = new Scanner(System.in);
        int days = scanner.nextInt(); // <Liczba dni symulacji (int)>

        int stopCapacity = scanner.nextInt(); // <Pojemność przystanku (int)>
        int stopsNum = scanner.nextInt(); // <Liczba przystanków (int)>
        //!!! STOPS ARRAY !!!//
        Stop[] stops = new Stop[stopsNum];

        for (int i = 0; i < stopsNum; i++) {
            String stopName = scanner.next(); // <Nazwa przystanku (String)>
            stops[i] = new Stop(stopName, stopCapacity);
        }
        // Sort stops by name for binary search
        Arrays.sort(stops, (a, b) -> a.getName().compareTo(b.getName()));


        int passengersNum = scanner.nextInt(); //<Liczba pasażerów (int)>
        //!!! PASSANGERS ARRAY !!!//
        Passenger[] passengers = new Passenger[passengersNum];
        // Creation of passangers
        for (int i = 0; i < passengersNum; i++) {
            int stopIndex = Losowanie.losuj(0, stopsNum - 1);
            passengers[i] = new Passenger(stops[stopIndex], i);
        }


        // App.Entities.Tram parameters
        int tramCapacity = scanner.nextInt(); // <Pojemność tramwaju (int)>
        int routeNum = scanner.nextInt(); // <Liczba linii tramwajowych (int)>
        //!!! ROUTES ARRAY !!!//
        TramLine[] routes = new TramLine[routeNum];

        for (int i = 0; i < routeNum; i++) {
            int tramsNum = scanner.nextInt();
            int routeLen = scanner.nextInt();
            int[] timeTravel = new int[routeLen+1];
            RouteSegment[] routeStops = new RouteSegment[routeLen];
            //!!! ROUTES ARRAY !!!//
            Tram[] trams = new Tram[tramsNum];

            // Creating of route stops(segments)
            for (int j = 0; j < routeLen; j++) {
                String stopName = scanner.next();
                int travelTimeFrom = scanner.nextInt();
                int travelTimeTo = 0;
                if (j > 0) {
                    travelTimeTo = routeStops[j - 1].getTravelTimeFrom();
                }
                routeStops[j] = new RouteSegment(findStopByNameBinary(stops, stopName), travelTimeTo, travelTimeFrom);
            }
            if (routeLen > 0) {
                routeStops[0].setTravelTimeTo(routeStops[routeLen-1].getTravelTimeFrom());
            }


            // Calculate travel space
            int sumOfTravelTime = 0;
            for (RouteSegment travelTime : routeStops) {
                sumOfTravelTime += travelTime.getTravelTimeTo();
                // Wypisz czas dojazdu do przystanku
//                System.out.println("Stop: " + travelTime.getName() + ", Time TO: " + travelTime.getTravelTimeTo() + ", Time FROM: " + travelTime.getTravelTimeFrom());
            }
            // W obie strony
            sumOfTravelTime *= 2;
            int travelSpace = sumOfTravelTime / tramsNum;

            routes[i] = new TramLine(tramsNum, routeStops, i);
            int travelSpaceToStop = 0;
            int minSum = 0;

            // Creating of all trams
            for (int j=0; j < tramsNum; j++){
                // 1 jadą od 0 do routeStops
                // -1 jadą od routeStops do 0
                int direciton;
                // Jezeli dotrze do srodka, chce wyzerowac odstep, bo rozumiem, ze odstepy sa gdy w jednym kierunku jada kilka tramwajow
                if (j == (tramsNum+1) / 2) {
                    travelSpaceToStop = 0;
                }

                // Chce przeblizenie w gore
                direciton = (j < (tramsNum+1) / 2) ? 1 : -1;
                trams[j] = new Tram(tramCapacity, i*tramsNum+j, direciton,  routes[i]);
                trams[j].setDefaultLocalTime(360+travelSpaceToStop);
                trams[j].setLocalTime(360+travelSpaceToStop);
                int sum = 0;
                for (int k = 0; k < routeLen*2; k++) {
                    // Zrobie cala petle, zeby obliczyc czas dojazdu do przystanku
                    // Zakladam, ze tramwaj na poczatku jest na zjezdni, czyli na -1, a potem pojedzie na przystanek faktyczny 0
                    int timeToNextStop = trams[j].getTimeToNextStopByDistance(k+1);
                    sum += timeToNextStop + travelSpaceToStop;
                    Stop tramNextStop = trams[j].getStopByDistance(k+1);
                    StopDistance stopDistance = new StopDistance(tramNextStop, sum);
                    trams[j].addStopDistance(stopDistance);
                }
                travelSpaceToStop += travelSpace;

                // Tworzenie rozkladu dla przystankow
                // Pobieram cykliczne opoznienie
                if (minSum == 0 || sum < minSum) {
                    minSum = sum;
                    routes[i].setCycleTime(minSum);
                }
            }
            routes[i].addTrams(trams);
        }

        scanner.close();
        return new SceneData(days, stops, passengers, routes);
    }

    private static Stop findStopByNameBinary(Stop[] stops, String name) {
        int low = 0;
        int high = stops.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            Stop midVal = stops[mid];
            int cmp = midVal.getName().compareTo(name);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return midVal;
        }
        return null;
    }
}