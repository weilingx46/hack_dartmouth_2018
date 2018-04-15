package hack.dartmouth.squad;

public class Data {
    String user;
    String lat;
    String lng;

    public Data(String user, String lat, String lng) {
        this.user = user;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUser(){
        return user;
    }

    public String getLat(){
        return lat;
    }

    public String getLng(){ return lng; }
}
