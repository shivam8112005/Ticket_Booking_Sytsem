import java.util.HashMap;

class Bus {
    static int busCounter = 1;
    static HashMap<Bus, Route> busRoutes=new HashMap<>();
    private int busId;
    private String numberPlate;
    public Bus(String numberPlate, Route route) {
        this.busId = busCounter++;
        this.numberPlate = numberPlate;
        this.busRoutes.put(this, route);
    }
    public void addRoute(Route route) {
        this.busRoutes.put(this, route);
    }
    public Route getRoute(int routeId) {
        return busRoutes.get(this);
    }
    public int getBusId() {
        return this.busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getNumberPlate() {
        return this.numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public HashMap<Bus, Route> getAllRoutes() {
        return busRoutes;
    }

    public void setRoute(Route route) {
      busRoutes.put(this, route);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busId=" + busId +
                ", numberPlate='" + numberPlate + '\'' +
                ", routes=" + busRoutes.get(this) +
                '}';
    }
}
