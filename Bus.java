import java.util.HashMap;

class Bus {
    private int busId;
    private String numberPlate;
    private HashMap<Integer, Route> routes;

    public Bus(int busId, String numberPlate, Route route) {
        this.busId = busId;
        this.numberPlate = numberPlate;
        this.routes = new HashMap<>();
        this.routes.put(route.getRouteId(), route);
    }

    public void addRoute(Route route) {
        this.routes.put(route.getRouteId(), route);
    }

    public Route getRoute(int routeId) {
        return this.routes.get(routeId);
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public HashMap<Integer, Route> getRoutes() {
        return routes;
    }

    public void setRoutes(HashMap<Integer, Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busId=" + busId +
                ", numberPlate='" + numberPlate + '\'' +
                ", routes=" + routes +
                '}';
    }
}
