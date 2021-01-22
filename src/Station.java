import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Station implements Model{

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


    public static ArrayList<Station> get(DB_connect db) throws SQLException {
        ArrayList<Station> stations = new ArrayList<>();
        db.query("SELECT * FROM `stations`");

        ResultSet rs = db.getResult();
        while (rs.next()) {
            Station station = (Station) new Station().convertFromResultSet(rs);
            stations.add(station);
        }

        return stations;
    }


    @Override
    public String toString() {
        return "{" +
                "\"stn\":\"" + stn + '"' +
                ", \"name\":\"" + name + '"' +
                ", \"country\":\"" + country + '"' +
                ", \"latitude\":" + latitude +
                ", \"longitude\":" + longitude +
                ", \"elevation\":" + elevation +
                '}';
    }

    @Override
    public Model convertFromResultSet(ResultSet resultSet) throws SQLException {
        return new Station(resultSet.getInt("stn"), resultSet.getString("name"), resultSet.getString("country"), resultSet.getDouble("latitude"), resultSet.getDouble("longitude"), resultSet.getDouble("elevation"));
    }
}
