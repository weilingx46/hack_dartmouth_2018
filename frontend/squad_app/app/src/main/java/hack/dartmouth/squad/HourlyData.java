package hack.dartmouth.squad;

public class HourlyData {
    int hour; // valid values: 0-23
    double distance;

    public HourlyData(int hour) {
        this.hour = hour;
        this.distance = 0;
    }

    public String getDistance(){
        return String.valueOf(distance);
    }

    @Override
    public String toString() {
        return String.format("hour: " + hour + ", distance: " + distance);
    }
}
