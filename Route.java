import java.util.Arrays;

class Route {
    //java.sql.Time routeTime = resultSet.getTime("route_time");
    static int routeCounter=1;
    private int routeId;
    private String startLocation;
    private String startTime;
    private String endLocation;
    private String endTime;
    private boolean[] daysOfWeek; // true if the route runs on that day, false otherwise

    public Route(int routeId, String startLocation, String startTime, String endLocation, String endTime, boolean[] daysOfWeek) {
        this.routeId = routeCounter++;
        this.startLocation = startLocation;
        this.startTime = startTime;
        this.endLocation = endLocation;
        this.endTime = endTime;
        this.daysOfWeek = daysOfWeek;
    }

    public int getRouteId() {
        return this.routeId;
    }

    // public void setRouteId(int routeId) {
    //     this.routeId = routeId;
    // }

    public String getStartLocation() {
        return this.startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndLocation() {
        return this.endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean[] getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public void setDaysOfWeek(boolean[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getName() {
        return this.startLocation + " to " + this.endLocation;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", startLocation='" + startLocation + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", endTime='" + endTime + '\'' +
                ", daysOfWeek=" + Arrays.toString(daysOfWeek) +
                '}';
    }
}
