import org.w3c.dom.Element;

import java.sql.*;
import java.util.ArrayList;

public class Measurement{

    private static String STATION_STN = "STN";
    private static String DATE = "DATE";
    private static String TIME = "TIME";
    private static String TEMPERATURE = "TEMP";
    private static String DEW_POINT = "DEWP";
    private static String AIR_PRESSURE_ON_STATION_LEVEL = "STP";
    private static String AIR_PRESSURE_ON_SEA_LEVEL = "SLP";
    private static String VISIBILITY = "VISIB";
    private static String WIND_SPEED = "WDSP";
    private static String RAINFALL = "PRCP";
    private static String SNOW = "SNDP";
    private static String FRSHTT = "FRSHTT";
    private static String CLDC = "CLDC";
    private static String WNDDIR = "WNDDIR";

    public int station_stn;

    public String date;

    public String time;

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

    private Element element;

    public Measurement() {
    }

    public Measurement(Element element) {
        this.element = element;
        this.station_stn = Integer.parseInt(this.getTextFromElement(STATION_STN));
        this.date = this.getTextFromElement(DATE);
        this.time = this.getTextFromElement(TIME);
        this.temperature = Double.parseDouble(this.getTextFromElement(TEMPERATURE));
        this.dew_point = Double.parseDouble(this.getTextFromElement(DEW_POINT));
        this.air_pressure_on_station_level = Double.parseDouble(this.getTextFromElement(AIR_PRESSURE_ON_STATION_LEVEL));
        this.air_pressure_on_sea_level = Double.parseDouble(this.getTextFromElement(AIR_PRESSURE_ON_SEA_LEVEL));
        this.visibility = Double.parseDouble(this.getTextFromElement(VISIBILITY));
        this.wind_speed = Double.parseDouble(this.getTextFromElement(WIND_SPEED));
        this.rainfall = Double.parseDouble(this.getTextFromElement(RAINFALL));
        this.snow = Double.parseDouble(this.getTextFromElement(SNOW));
        this.frshtt = Integer.parseInt(this.getTextFromElement(FRSHTT));
        this.cldc = Double.parseDouble(this.getTextFromElement(CLDC));
        this.wnddir = Integer.parseInt(this.getTextFromElement(WNDDIR));
    }

    private String getTextFromElement(String prefix) {
        String result = element.getElementsByTagName(prefix).item(0).getTextContent();
        return result.isEmpty() ? "0" : result;
    }

    public void create(DB_connect db) throws SQLException {
        PreparedStatement pstmt = db.prepareQuery("INSERT INTO `measurement`(`station_stn`, `date`, `temperature`, `dew_point`, `air_pressure_on_station_level`, `air_pressure_on_sea_level`, `visibility`, `wind_speed`, `rainfall`, `snow`, `frshtt`, `cldc`, `wnddir`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt.setInt(1, this.station_stn);
        pstmt.setString(2, this.date + " " + this.time);
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

    public static ArrayList<String> getPage(DB_connect db, String query) throws SQLException {
        ArrayList<String> measurements = new ArrayList<>();
        db.query(query);

        ResultSet rs = db.getResult();
        while (rs.next()) {
            String measurement = new Measurement().convertFromResultSet(rs);
            measurements.add(measurement);
        }

        return measurements;
    }

    /**
     * TODO: IMPLEMENT
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public String convertFromResultSet(ResultSet resultSet) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            String name = resultSet.getMetaData().getColumnName(i);

            stringBuilder.append(i == 1 ? "\"" : ", \"");
            stringBuilder.append(name);
            stringBuilder.append("\":");

            switch (resultSet.getMetaData().getColumnTypeName(i)) {
                case "INT UNSIGNED":
                case "INT":
                    stringBuilder.append(resultSet.getInt(name));
                    break;
                case "VARCHAR":
                case "DATETIME":
                    stringBuilder.append("\"");
                    stringBuilder.append(resultSet.getString(name));
                    stringBuilder.append("\"");
                    break;
                case "DOUBLE":
                    stringBuilder.append(resultSet.getDouble(name));
                    break;
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
