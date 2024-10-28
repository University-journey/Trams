package App.Entities;

public class RouteSegment extends StopDistance {
    private int travelTimeFrom;
    public RouteSegment(Stop stopA, int travelTimeToA, int travelTimeFromA) {
        super(stopA, travelTimeToA);
        travelTimeFrom = travelTimeFromA;
    }
    public void setTravelTimeFrom(int travelTimeFrom) {
        this.travelTimeFrom = travelTimeFrom;
    }
    public int getTravelTimeFrom() {
        return travelTimeFrom;
    }

}
