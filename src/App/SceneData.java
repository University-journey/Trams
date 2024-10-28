package App;

import App.Entities.*;

public class SceneData {
    private int days;
    private Stop[] stops;
    private Passenger[] passengers;
    private TramLine[] routes;

    public SceneData(int days, Stop[] stops, Passenger[] passengers, TramLine[] routes) {
        this.days = days;
        this.stops = stops;
        this.passengers = passengers;
        this.routes = routes;
    }

    public int getDays() {
        return days;
    }

    public Stop[] getStops() {
        return stops;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public TramLine[] getRoutes() {
        return routes;
    }
}