import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Station {

    public int stn;

    public String name;

    public String country;

    public Double latitude;

    public Double longitude;

    public Double elevation;

    public Station() {

    }

    public Station(int stn, String name, String country, Double latitude, Double longitude, Double elevation) {
        this.stn = stn;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }



    public static List<Station> get(DB_connect db) throws SQLException {
        ArrayList<Station> stations = new ArrayList<>();
        db.query("SELECT * FROM `stations`");

        ResultSet rs = db.getResult();

        while (rs.next()) {
            Station station = new Station(rs.getInt("stn"), rs.getString("name"), rs.getString("country"), rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getDouble("elevation"));
            stations.add(station);
        }

        return stations;
    }


    @Override
    public String toString() {
        return "Station{" +
                "stn=" + stn +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                '}';
    }
}
