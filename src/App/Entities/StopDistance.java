package App.Entities;

public class StopDistance {
    protected Stop stop;
    protected int travelTimeTo;
    public StopDistance(Stop stopA, int travelTimeFromA) {
        stop = stopA;
        travelTimeTo = travelTimeFromA;
    }

    public String getName() {
        return stop.getName();
    }

    public void setTravelTimeTo(int travelTimeTo) {
        this.travelTimeTo = travelTimeTo;
    }
    public int getTravelTimeTo() {
        return travelTimeTo;
    }


    public int getCapacity() {
        return stop.getCapacity();
    }

    public Stop getStop() {
        return stop;
    }
}
