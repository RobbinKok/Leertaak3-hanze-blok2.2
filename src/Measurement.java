import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

public class Measurement {

    public int station_stn;

    public LocalDateTime date;

    public Double temperature;

    public Double dew_point;

    public Double air_pressure_on_station_level;

    public Double air_pressure_on_sea_level;

    public Double visibility;

    public Double wind_speed;

    public Double rainfall;

    public Double snow;

    public int frshtt;

    public Double cldc;

    public int wnddir;


    public Measurement(int station_stn, LocalDateTime date, Double temperature, Double dew_point, Double air_pressure_on_station_level, Double air_pressure_on_sea_level, Double visibility, Double wind_speed, Double rainfall, Double snow, int frshtt, Double cldc, int wnddir) {
        this.station_stn = station_stn;
        this.date = date;
        this.temperature = temperature;
        this.dew_point = dew_point;
        this.air_pressure_on_station_level = air_pressure_on_station_level;
        this.air_pressure_on_sea_level = air_pressure_on_sea_level;
        this.visibility = visibility;
        this.wind_speed = wind_speed;
        this.rainfall = rainfall;
        this.snow = snow;
        this.frshtt = frshtt;
        this.cldc = cldc;
        this.wnddir = wnddir;
    }


    public void create(DB_connect db) throws SQLException {
        PreparedStatement pstmt = db.prepareQuery("INSERT INTO `measurement`(`station_stn`, `date`, `temperature`, `dew_point`, `air_pressure_on_station_level`, `air_pressure_on_sea_level`, `visibility`, `wind_speed`, `rainfall`, `snow`, `frshtt`, `cldc`, `wnddir`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt.setInt(1, this.station_stn);
        pstmt.setString(2, "2021-01-17 14:21:56");
        pstmt.setDouble(3, this.temperature);
        pstmt.setDouble(4, this.dew_point);
        pstmt.setDouble(5, this.air_pressure_on_station_level);
        pstmt.setDouble(6, this.air_pressure_on_sea_level);
        pstmt.setDouble(7, this.visibility);
        pstmt.setDouble(8, this.wind_speed);
        pstmt.setDouble(9, this.rainfall);
        pstmt.setDouble(10, this.snow);
        pstmt.setInt(11, this.frshtt);
        pstmt.setDouble(12, this.cldc);
        pstmt.setInt(13, this.wnddir);

        pstmt.execute();
    }
}
