package App.Entities;

public class TramLine {
    private RouteSegment[] travelTimes;
    private Tram[] trams;
//    private int[] travelTimes;
    private int lineName;
    private int cycleTime;

    public TramLine(int tramsNum, RouteSegment[] tramTravelTimesA, int n) {
        travelTimes = tramTravelTimesA;
//        tramTravelTimes = tramTravelTimesA;
        trams = new Tram[tramsNum];
        lineName = n;
    }

//    public void scheduleTram() {
//        // Schedules trams on the line
//
//    }

    public void addTrams(Tram[] tramsA){
        trams = tramsA;
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public RouteSegment[] getSegments() {
        return travelTimes;
    }
    public int getLength() {
        return travelTimes.length;
    }

    public Tram[] getTrams() {
        return trams;
    }

    public Tram getTram(int i) {
        return trams[i];
    }

    public int getTramsNum() {
        return trams.length;
    }

    public int getLineName() {
        return lineName;
    }


    public RouteSegment[] getStops() {
        return travelTimes;
    }


    public int getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime() {
        this.cycleTime = cycleTime;
    }
}